package example.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

public class Eagerly {

    public static <A, B> List<B> transform(List<A> input, Function<A, B> function) {
        // use newArrayList since transform returns a lazy list and we want it to happen *now*
        return Lists.newArrayList(Lists.transform(input, function));
    }
}
