package net.ripe.rpki.commons.crypto.cms.roa;

import com.google.common.annotations.VisibleForTesting;
import lombok.EqualsAndHashCode;
import net.ripe.ipresource.IpRange;
import net.ripe.rpki.commons.util.EqualsSupport;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.CheckForNull;
import java.io.Serializable;
import java.util.Comparator;

@EqualsAndHashCode
public class RoaPrefix implements Serializable, Comparable<RoaPrefix> {
    private static final Comparator<RoaPrefix> ROA_PREFIX_COMPARATOR = Comparator.comparing(RoaPrefix::getPrefix)
            .thenComparing(RoaPrefix::getMaximumLength, Comparator.nullsFirst(Comparator.naturalOrder()));
    private static final long serialVersionUID = 1L;

    private final IpRange prefix;
    @CheckForNull
    private final Integer maximumLength;

    public RoaPrefix(IpRange prefix) {
        this(prefix, null);
    }

    /**
     * Instantiate an RoaPrefix.
     *
     * @param prefix prefix of the ROA
     * @param maximumLength maximumLength of the ROA
     * @ensures that the maximumLength is valid compared to the prefix and for the address family of the prefix.
     */
    public RoaPrefix(IpRange prefix, Integer maximumLength) {
        Validate.notNull(prefix, "prefix is required");
        Validate.isTrue(prefix.isLegalPrefix(), "prefix is not a legal prefix");
        Validate.isTrue(maximumLength == null || (maximumLength >= prefix.getPrefixLength() && maximumLength <= prefix.getType().getBitSize()),
                "maximum length not in range");

        this.prefix = prefix;
        this.maximumLength = maximumLength;
    }

    public IpRange getPrefix() {
        return prefix;
    }

    public Integer getMaximumLength() {
        return maximumLength;
    }

    public int getEffectiveMaximumLength() {
        return maximumLength != null ? maximumLength : getPrefix().getPrefixLength();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("prefix", getPrefix()).append("maximumLength", maximumLength).toString();
    }

    @Override
    public int compareTo(RoaPrefix o) {
        return ROA_PREFIX_COMPARATOR.compare(this, o);
    }
}
