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
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyReader;

import uk.co.caprica.image.service.ImageService;

/**
 *
 */
public class JaxrsImageService implements ImageService {

    /**
     *
     */
    private final Client client;

    /**
     *
     */
    private final String[] acceptedResponseTypes;

    /**
     *
     */
    private final MessageBodyReader<?> imageMessageBodyReader;

    /**
     *
     */
    public JaxrsImageService() {
        this.client = ClientBuilder.newClient();
        this.acceptedResponseTypes = getKnownContentTypes();
        this.imageMessageBodyReader = new BufferedImageMessageBodyReader(acceptedResponseTypes);
    }

    @Override
    public BufferedImage image(String url) {
        return client
            .register(imageMessageBodyReader)
            .target(url)
            .request(acceptedResponseTypes)
            .get(BufferedImage.class);
    }

    /**
     *
     *
     * @return
     */
    private String[] getKnownContentTypes() {
        List<String> knownContentTypes = new ArrayList<>();
        MimetypesFileTypeMap map = new MimetypesFileTypeMap();
        for (String supportedImageType : ImageIO.getReaderFileSuffixes()) {
            String name = String.format("a.%s", supportedImageType);
            String contentType = map.getContentType(name);
            if (!contentType.equals(MediaType.APPLICATION_OCTET_STREAM)) {
                knownContentTypes.add(contentType);
            }
        }
        return knownContentTypes.toArray(new String[knownContentTypes.size()]);
    }
}
