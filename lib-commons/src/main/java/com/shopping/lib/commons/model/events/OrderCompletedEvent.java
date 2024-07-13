package com.shopping.lib.commons.model.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderCompletedEvent extends Event {}
