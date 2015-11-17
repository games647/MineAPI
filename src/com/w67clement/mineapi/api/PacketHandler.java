package com.w67clement.mineapi.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.w67clement.mineapi.enums.PacketList;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler
{

	PacketList listenType() default PacketList.ALL;
}
