/**
 * The BSD License
 *
 * Copyright (c) 2010-2018 RIPE NCC
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
package net.ripe.rpki.commons.validation.objectvalidators;

import net.ripe.ipresource.IpResourceSet;
import net.ripe.rpki.commons.crypto.crl.X509Crl;
import net.ripe.rpki.commons.crypto.x509cert.X509ResourceCertificate;
import net.ripe.rpki.commons.validation.ValidationOptions;
import net.ripe.rpki.commons.validation.ValidationResult;

public class ResourceValidatorFactory {

    public static X509ResourceCertificateParentChildValidator getX509ResourceCertificateStrictValidator(
            CertificateRepositoryObjectValidationContext context,
            ValidationOptions options, ValidationResult result, X509Crl crl) {

        return new X509ResourceCertificateParentChildValidator(options, result, context.getCertificate(), crl, context.getResources());
    }

    public static X509ResourceCertificateValidator getX509ResourceCertificateValidator(
            CertificateRepositoryObjectValidationContext context,
            ValidationOptions options, ValidationResult result, X509Crl crl) {

        if (options.isLooseValidationEnabled())
            return new X509ResourceCertificateParentChildLooseValidator(options, result, crl, context);

        return new X509ResourceCertificateParentChildValidator(options, result, context.getCertificate(), crl, context.getResources());
    }

    public static X509ResourceCertificateParentChildValidator getX509ResourceCertificateParentChildStrictValidator(
            ValidationOptions options, ValidationResult result, X509ResourceCertificate parent,
            IpResourceSet resources, X509Crl crl) {
        return new X509ResourceCertificateParentChildValidator(options, result, parent, crl, resources);
    }
}
