package pl.edu.agh.io.dzikizafrykibackend.model;

import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Value.Style(
        create = "new",
        typeAbstract = "Abstract*",
        typeModifiable = "*",
        get = {"is*", "get*"},
        init = "set*"
)
public @interface DtoStyle {

}
