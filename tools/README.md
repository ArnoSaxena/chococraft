# Tools

## Generate Textures

ImageMagick 6.7.5 is needed to use this tool.

This is currently only set up for adult Chocobos, Chicabos will need to be done manually for now, but I feel we don't need that at this time.

You can run generate texture like this
```$ ./generate_texture.sh```
An output directory will be created, all files in there can be copied into choco folder.

To add another Chocobo color, just create a new Chocobo in the Chocobo folder, when you run generate, it will be generate the appropriate textures for it.

To add another texture overlay, like with saddle bags, create the overlay in the Textures folder.  You can look there to see how it should be made.

To add another variation to a Chocobo, in this case male and female, just create the appropriate texture in the variations folder.

Note: All texture sizes must be the same in order for them to be overlayed properly.

## Get Source

Get source allows you to copy source code to and from MCP and the repo.
You can run it like this
```$ ./get_source.sh```
By default, it will ask you for your MCP folder every time, but if you create a variable called MCP_DIR it won't ask you where MCP is every time.

If you don't pass anything in, it will ask if you want to copy frommcp or fromrepo.  So, frommcp will copy your changes from MCP and Eclipse to the repo to be committed, this should be run after you want to commit changes to the repo.  And fromrepo will copy the changes from the repo to MCP, you would call this after you pulled commits down and want to load them in Eclipse or whatever.

Currently, if you want to pull in a new source file, you'll have to add it to the repo folder like this ```touch NewFile.java``` and now you can run frommcp and it will pull it in to be commited.

Alternatively, you can call both frommcp and fromrepo when you run the file like this
```$ ./get_source.sh frommcp```
```$ ./get_source.sh fromrepo```

