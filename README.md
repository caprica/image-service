image-service
=============

Simple service to access images from public public web services.

This service was primarily designed to be used to get movie poster and backdrop
(or still) images from themoviedb.org or Rotten Tomatoes.

Usage is trivial:
```
ImageServce imageService = new JaxrsImageService();
BufferedImage image = imageService.image("https://image.tmdb.org/t/p/original/tAXARVreJnWfoANIHASmgYk4SB0.jpg");
```
You could simply use ImageIO to load an image URL directly - this service
implementation instead uses the JAX-RS standard client API to get images, and
provides an extension point that can be used to, amongst other things, add a
caching wrapper around the service.
