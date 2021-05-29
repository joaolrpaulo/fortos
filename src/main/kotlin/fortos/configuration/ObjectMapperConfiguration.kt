package fortos.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class ObjectMapperConfiguration {

    fun loadObjectMapper() = ObjectMapper(YAMLFactory()).registerKotlinModule()
}
