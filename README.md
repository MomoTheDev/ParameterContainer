# ParameterContainer
A lightweight Java library to map key-value entries

# Installation
1. Download the latest file from the releases section
2. Create a project in your favorite Java IDE
3. Add the downloaded file as a library in your project
4. Use and Enjoy!

# Usage
In order to use the library, we have to create a new `ParameterContainer`. We do that by calling the static `create()` method from the `ParameterHandler`-Class.
Here's an example of a plain `ParameterContainer`:
```java

import me.mohammad.parametercontainer.ParameterContainer;
import me.mohammad.parametercontainer.ParameterHandler;

public class ExampleClass {

    private ParameterContainer container;

    public ExampleClass() {
        container = ParameterHandler.create();
    }
  
}

```
We could add some values directly using the `create()` method. Just pass them as `ParameterEntry` instances.
Code Example:
```java

import me.mohammad.parametercontainer.ParameterContainer;
import me.mohammad.parametercontainer.ParameterHandler;

public class ExampleClass {

    private ParameterContainer container;

    public ExampleClass() {
        container = ParameterHandler.create(ParameterHandler.entry("name", "MomoTheSiM"));
    }
  
}

```

Another way of adding entries/values is by using the `add(Entry)` or the `add(Key, Value)` method.
Code Example:
```java

import me.mohammad.parametercontainer.ParameterContainer;
import me.mohammad.parametercontainer.ParameterHandler;

public class ExampleClass {

    private ParameterContainer container;

    public ExampleClass() {
        container = ParameterHandler.create();
        container.add("Price", 5);
        container.add(ParameterHandler.entry("someValue", 7));
    }
  
}

```

Now, that we have some values added, we can get them using the `get(Key)` method.
```java

    public Object getName() {
        return container.get("Name");
    }

```

We could also get primitive values instead of objects:
Here's a code example:
```java

    public String getName() {
        return container.getString("Name");
    }
    
    public Double getPrice() {
        return container.getInteger("Price");
    }

```

As the code above showed us, we can get a `String` as return, instead of an `Object`.
This can be done with every other primitive type. You just have to call the `getPRIMITIVE()` method, replace the `PRIMITIVE` with your own type name:
For Example:
```java

    // To get a float
    container.getFloat(key);
    
    // To get a Short
    container.getShort(key);
    
    //... Other Primitives ...

```


PS: Version 1.0.0 can't return a `char` primitive.

# Updating
I have enough time to manage this library by my own, you can suggest me some features you'd like or maybe report some bugs as well.
You can give suggestions and report bugs to me via Discord (MomoTheSiM#6478).

# Thanks
You can use this library, the way you'd like.
The only thing you're not allowed to do, is to call it yours, you could atleast give credit to Mohammad Alkhatib (MomoTheSiM) for developing it.
Thanks for using my library, consider supporting me by leaving a star!
