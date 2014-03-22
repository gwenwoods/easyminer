package org.ninjacat.easyminer.algorithm.tree.binary;

import java.util.HashSet;
import java.util.Set;

public class IsInPredicate implements Predicate {

    String field = null;
    Set<String> values = new HashSet<String>();

    public IsInPredicate(String field, Set<String> values) {
        super();
        this.field = field;
        this.values.addAll(values);
    }

}
