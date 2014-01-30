/*
 * This file is part of image-service.
 *
 * media-scanner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * image-service is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with image-service.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2014 Caprica Software Limited.
 */

package uk.co.caprica.image.service.jaxrs;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

/**
 *
 */
public final class BufferedImageMessageBodyReader implements MessageBodyReader<BufferedImage> {

    /**
     *
     */
    private final String[] acceptedResponseTypes;

    /**
     *
     *
     * @param acceptedResponseTypes
     */
    public BufferedImageMessageBodyReader(String[] acceptedResponseTypes) {
        this.acceptedResponseTypes = acceptedResponseTypes;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (type == BufferedImage.class) {
            String contentType = mediaType.toString();
            for (String acceptedResponseType : acceptedResponseTypes) {
                if (acceptedResponseType.equals(contentType)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BufferedImage readFrom(Class<BufferedImage> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        return ImageIO.read(entityStream);
    }
}
