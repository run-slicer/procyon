# procyon

A JavaScript port of the [Procyon decompiler](https://github.com/mstrobel/procyon).

## Example

```js
const fs = require("fs");
const { decompile } = require("./procyon.js"); // get it from the dist/ directory or jsDelivr

const data = fs.readFileSync("./your/package/HelloWorld.class"); // read a class file
console.log(await decompile("your/package/HelloWorld", {
    source: async (name) => {
        /* provide classes for analysis here, including the one you want to decompile */

        console.log(name); /* internal name, e.g. java/lang/Object */
        return name === "your/package/HelloWorld" ? data : null /* class not available */;
    },
    options: {
        /* see https://github.com/run-slicer/procyon/blob/main/src/main/java/run/slicer/procyon/impl/ProcyonOptions.java */
    },
}));
```

Or see the browser-based proof-of-concept in the [docs](./docs) directory.

## Licensing

The supporting code for this project is licensed under the [MIT License](./LICENSE).
The Procyon decompiler is licensed under the [Apache License 2.0](./LICENSE-PROCYON).

_This project is not affiliated with, maintained or endorsed by the Procyon project in any way.
Do NOT report issues with this project to the Procyon issue tracker._
