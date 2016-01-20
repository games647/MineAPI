package com.w67clement.mineapi.api;

import com.w67clement.mineapi.enums.PacketList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler
{

	PacketList listenType() default PacketList.ALL;
}
