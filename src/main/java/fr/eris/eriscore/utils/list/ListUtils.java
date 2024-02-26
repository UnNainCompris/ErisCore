package fr.eris.eriscore.utils.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUtils {

    @SafeVarargs
    public static <T> List<T> concatList(List<T>... allList) {
        List<T> newList = new ArrayList<>();
        for(List<T> list : allList)
            newList.addAll(list);
        return newList;
    }

}
