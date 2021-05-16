package com.minecraftdimensions.slashserver.configlibrary;

import java.util.Map;

public interface ConfigurationSerializable {

    Map<String, Object> serialize();
}