package com.example.log4j2.customlayout;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

import java.nio.charset.Charset;
import java.util.Map;

@Plugin(name = "CustomLayout", category = "Core", elementType = "layout", printObject = true)
public class CustomLayout extends AbstractStringLayout {

    private CustomLayout(Charset charset) {
        super(charset);
    }

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder str = new StringBuilder();
        str.append(event.getLevel())
           .append(" ")
           .append(event.getTimeMillis())
           .append(" ")
           .append(event.getThreadName())
           .append(" [")
           .append(ThreadContext.get("correlationID")) 
           .append("] ")
           .append(event.getLoggerName())
           .append(": ")
           .append(event.getMessage().getFormattedMessage())
           .append("\n");
        
        return str.toString();
    }

    @PluginBuilderFactory
    public static CustomLayout.Builder newBuilder() {
        return new CustomLayout.Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<CustomLayout> {
        @PluginBuilderAttribute
        private Charset charset = Charset.defaultCharset();

        @Override
        public CustomLayout build() {
            return new CustomLayout(charset);
        }

        public Builder setCharset(Charset charset) {
            this.charset = charset;
            return this;
        }
    }
}
