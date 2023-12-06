package com.neilw.postplatform.base.collector;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class SQLNumberInCollector implements Collector<Number, List<String>, String> {
    @Override
    public Supplier<List<String>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<String>, Number> accumulator() {
        return (list, n) -> list.add(Optional.ofNullable(n).map(Number::toString).orElse(null));
    }

    @Override
    public BinaryOperator<List<String>> combiner() {
        return (l1, l2) -> {
            ArrayList<String> l = new ArrayList<>();
            l.addAll(l1);
            l.addAll(l2);
            return l;
        };
    }

    @Override
    public Function<List<String>, String> finisher() {
        return l -> String.join(",", l);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Sets.newHashSet(Characteristics.CONCURRENT);
    }
}
