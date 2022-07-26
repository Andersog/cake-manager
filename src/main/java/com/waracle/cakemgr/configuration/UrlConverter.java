package com.waracle.cakemgr.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.net.URL;

/**
 * Custom converter for writing URLs as strings.
 */
@WritingConverter
public class UrlConverter implements Converter<URL, String> {
    @Override
    public String convert(URL source) {
        return source.toString();
    }
}
