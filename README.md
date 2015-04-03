# jadecR
The java DTO-Entity converteR

# Motivation
Most often, when writing backend applications, that somehow store data and also communicate data with the outside world, you want to separate your business models from the objects you share. And so, you have to convert them back and forth all the time. I stumbled across some solutions on how to ease the pain when doing so, but there wans't really something I found, was nice.

And so, here is a try to fix this.

# How it works
There is a [`@Convertible`](src/main/java/dwalldorf/jadecr/Convertible.java) annotation, you have to add to every class you want to be convertible. Also, the annotation takes a  `destClass` argument, asking for the compliant class, it should be converted to.

Imagine you have a `User` model and a `UserDto`, to communicate the user to the outside world. 

You would annotate the `User` model as follows: `@Convertible(destClass = UserDto.class)`. You can now pass the `User` model to the [converter](src/main/java/dwalldorf/jadecr/PojoConverter.java), and it will know, which class you want it to be converted to. 

Similarily to this, you will want to annotate your `UserDto`: `@Convertible(destClass = User.class)`. So you can convert them each way. 

This also works for any `@Convertile` class, that in itslef, contains another `@Convertible` annotated class. The Converter will use itself to convert any child `@Convertible` objects.

# How to use it
Configure the converter (optional): 

    ConverterFactory.configureType({type});

The default configuration is `ConverterType.GETTER_SETTER`
See available [types](src/main/java/dwalldorf/jadecr/ConverterType)


Obtain an instance:

    ConverterFactory.getInstance();

This will return a singleton instance of the configured type.


Convert a `@Convertible` annotated object: 

    // user is annotated: @Convertible(destClass = UserDto.class)
    UserDto userDto = (UserDto) converter.convert(user);

In case an error occurs while converting an object, the converter will throw a `ConversionException`. It contains the originally thrown `Exception`. As of now, there is no sophisticated exception handling / prevention. If you find a bug, feel free to contribute and send a pull request! :)

# License
See the [license](LICENSE)
