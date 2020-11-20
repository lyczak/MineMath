# Woah, this looks cool!

## What is it?
MineMath is a multivariate function plotting toolkit to help
students develop mathematics and 3D visualization skills
through Minecraft.

You can think of this as a **3D graphing calculator in Minecraft**.
MineMath is [Bukkit plugin](https://gaming.stackexchange.com/a/16573) and can be installed on Bukkit/Spigot-compatible
Minecraft server. I recommend [Paper](https://papermc.io).

## What can I do with it?
MineMath is still **under active development** but many basic
features are supported out of the box. So far it can do most
of the plot-related things I can do on my [TI-89](https://en.wikipedia.org/wiki/TI-89_series)
graphing calculator (though none of that fancy algebraic stuff).

### Features
- Defining plotting areas using an in-game wand tool
    - `/m2 defplot`
- Plotting [parametric curves](https://en.wikipedia.org/wiki/Parametric_equation#Examples_in_three_dimensions)
    - `/m2 plot cos(pi*u) sin(pi*u) u/2`
- Plotting bivariate and [parametric surfaces](https://en.wikipedia.org/wiki/Parametric_surface)
    - `/m2 plot e^(-x^2-y^2)`
    - `/m2 plot cos(pi*u) v*sin(pi*u) u-v`
- Plotting animated time-varying curves and surfaces!
    - `/m2 option animate true`
    - `/m2 option duration 5.0`
    - `/m2 plot cos(pi*u*t) sin(pi*u*t) u*t`
- Drawing color-coded, appropriately-scaled axes in your plots
- Setting the window size and evaluation ranges
    - `/m2 range x -1 1`
    - `/m2 range u 0 2`
    - `/m2 range t 0 3.14`
- Defining custom functions to use in your plots
    - `/m2 defun d(a,b)=a*cos(pi*b)`
    - `/m2 plot d(x,y)`

### In-Progress Features
A few other features have been implemented in code but aren't (yet)
available in game.
- A number of settings relating to plots that aren't yet changable in game
    - Un-swapping the Y and Z axis
    - Drawing the axes somewhere else
- Matrix inversion and multiplication allowing
for change-of-basis via linear transformations
- The `PlotMaterialProvider` interface, allowing plots
to be made of different types of blocks
    - This includes `DefaultMaterialProvider` which has some basic
    presets to make plots out of different colors of glass and wool.
- The `PlayerInstruction` interface, allowing easy hooking of `PlayerEvent`
objects to handle actions that players perform other than commands.
    - `PlaterInstruction<E>` objects are stored in a `PlayerSession`
    - The `instructionQueue` holds an ordered
    set of steps to be followed
    - The `instructionList` holds free
    instructions that can be run at any time

### Future Plans
Here are some ideas I have and things I might want to add or change.
- Session and plot persistence (probably in a file)
- Choosing between multiple plot areas without having
to reselect them with the wand
- Drawing a cube-like frame around the plotting area
- Polishing time-varying animated plots
- Adding size constraints to plotting areas
- Making more things asynchronous and timing-safe
- Adding tools users can use to manipulate their plots
    - Zoom, rotate, pan, redraw, etc.
- Allowing users to share plotting areas between each other
- Binding plots to tools to allow users to quickly switch plots
- Adding funky plotting colors via `PlotMaterialProvider`
- Who knows what else...?