# Woah, this looks cool!
MineMath is a multivariate function plotting toolkit to help
students develop 3D visualization skills through Minecraft.

You can think of this as a **3D graphing calculator in Minecraft**.
MineMath is [Bukkit plugin](https://gaming.stackexchange.com/a/16573) and can be installed on Bukkit/Spigot-compatible
Minecraft server. We recommend [Paper](https://papermc.io).

## What can I do with it?
MineMath supports the following features out of the box...
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
