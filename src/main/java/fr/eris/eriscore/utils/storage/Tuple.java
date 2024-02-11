package fr.eris.eriscore.utils.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Tuple<A, B> {
    @Setter private A a;
    @Setter private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
