# *What color is under my mouse* app (Pro Edition)
A high-performance Java utility for pixel-perfect color sampling.

## Goals
- **Real-time Color Tracking:** Display the current color under the mouse in #RRGGBB and #HHSSBB formats.
- **Precision Magnifier:** Provide a zoomed-in view of the pixels surrounding the cursor to ensure pinpoint accuracy.
- **Visual Feedback:** Include a blinking crosshair within the magnifier to identify the exact sampled pixel.
- **High-Performance Loop:** Utilize a dedicated thread (~125 FPS) for smooth, low-latency updates.
- **Lightweight UI:** Run as a dedicated, always-on-top popup at the bottom-right of the screen.

## Technical Approach
- **Engine:** AWT-based `Frame` using a custom "game loop" (`Runnable`) for rendering.
- **Rendering:** Manual double-buffering with `BufferedImage` to eliminate flicker.
- **Sampling:** `java.awt.Robot` for both pixel color data and screen region capture.
- **Interaction:** Single-click on the interface to immediately terminate the application.
