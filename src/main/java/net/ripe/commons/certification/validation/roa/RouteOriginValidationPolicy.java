/**
 * The BSD License
 *
 * Copyright (c) 2010, 2011 RIPE NCC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the RIPE NCC nor the names of its contributors may be
 *     used to endorse or promote products derived from this software without
 *     specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.ripe.commons.certification.validation.roa;

import java.util.List;

import net.ripe.commons.certification.cms.roa.Roa;
import net.ripe.ipresource.IpRange;


/**
 * See http://tools.ietf.org/html/draft-ietf-sidr-roa-validation-10
 */
public class RouteOriginValidationPolicy {

    /**
     * Precondition: roas are valid
     */
    public RouteValidityState determineRouteValidityState(AnnouncedRoute route, List<? extends Roa> roas) {
        return determineRouteValidityState(RtrPrefix.getAll(roas), route);
    }

    public RouteValidityState determineRouteValidityState(List<RtrPrefix> rtrPrefixes, AnnouncedRoute route) {
        RouteValidityState result = RouteValidityState.UNKNOWN;
        for (RtrPrefix rtrPrefix : rtrPrefixes) {
            switch (validateForRtrPrefix(route, rtrPrefix)) {
            case VALID:
                return RouteValidityState.VALID;
            case INVALID:
                result = RouteValidityState.INVALID;
                break;
            case UNKNOWN:
                break;
            }
        }
        return result;
    }

    /**
     * Precondition: roa is valid
     */
    // public RouteValidityState determineRouteValidityState(AnnouncedRoute
    // route, Roa roa) {
    // RouteValidityState result = RouteValidityState.UNKNOWN;
    // for(RoaPrefix roaPrefix: roa.getPrefixes()) {
    // RouteValidityState prefixValidationResult = validateRoaPrefix(route,
    // roaPrefix, roa.getAsn());
    // if (prefixValidationResult == RouteValidityState.VALID) {
    // return RouteValidityState.VALID;
    // } else if (prefixValidationResult == RouteValidityState.INVALID) {
    // result = prefixValidationResult;
    // }
    // }
    // return result;
    // }

    private RouteValidityState validateForRtrPrefix(AnnouncedRoute route, RtrPrefix rtrPrefix) {
        
        IpRange announcedPrefix = route.getPrefix();
        
        if (!rtrPrefix.prefix.contains(announcedPrefix)) { // non-intersecting
                                                           // or
                                                           // covering-aggregate
            return RouteValidityState.UNKNOWN;
        } else if (announcedPrefix.getPrefixLength() <= rtrPrefix.maxLength) {
            if (route.getOriginAsn() != null && rtrPrefix.asn.equals(route.getOriginAsn())) {
                return RouteValidityState.VALID;
            } else {
                return RouteValidityState.INVALID;
            }
        }
        return RouteValidityState.INVALID;
    }

}
