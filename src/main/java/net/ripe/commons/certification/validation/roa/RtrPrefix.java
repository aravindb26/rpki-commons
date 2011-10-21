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

import java.util.ArrayList;
import java.util.List;

import net.ripe.commons.certification.cms.roa.Roa;
import net.ripe.commons.certification.cms.roa.RoaPrefix;
import net.ripe.ipresource.Asn;
import net.ripe.ipresource.IpRange;

public class RtrPrefix {
	
	public final IpRange prefix;
	public final Integer maxLength;
	public final Asn asn;
	
	public RtrPrefix(IpRange prefix, int maximumLength, Asn asn) {
	    this.prefix = prefix;
	    this.maxLength = maximumLength;
	    this.asn = asn;
	}
	
	public static List<RtrPrefix> getAll(List<? extends Roa> roas) {
		List<RtrPrefix> result = new ArrayList<RtrPrefix>();
		for (Roa roa : roas) {
			for (RoaPrefix roaPrefix: roa.getPrefixes()) {
				result.add(new RtrPrefix(roaPrefix.getPrefix(), roaPrefix.getEffectiveMaximumLength(), roa.getAsn()));
			}
		}
		return result;
	}
	
}
