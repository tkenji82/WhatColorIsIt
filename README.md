# WhatColorIsIt (Pro Edition)

A high-performance, precision Java utility for pixel-perfect color sampling directly from your screen.

## 🚀 Quick Start
Download and run the executable JAR immediately:

**[👉 Download WhatColorIsIt.jar](https://github.com/tkenji82/WhatColorIsIt/raw/master/WhatColorIsIt.jar)**

*Requires Java 8 or higher to be installed on your system.*

## ✨ Features
- **Real-time Color Tracking:** Instant HEX display in both `#RRGGBB` and `#HHSSBB` formats.
- **Precision Magnifier:** A zoomed-in view of the area surrounding your cursor for pinpoint accuracy.
- **Visual Feedback:** A blinking crosshair identifies the exact pixel being sampled.
- **High-Performance Engine:** Runs on a dedicated thread (~125 FPS) for zero-latency feedback.
- **Lightweight & Non-Intrusive:** Always-on-top popup that stays out of your way at the bottom-right of the screen.

## 🛠️ How to Use
1. **Run the App:** Double-click the `.jar` file or run `java -jar WhatColorIsIt.jar` from your terminal.
2. **Sample Colors:** Simply move your mouse anywhere on your screen. The popup will update instantly.
3. **Exit:** Click anywhere on the popup window to close the application.

## 📖 Technical Approach
- **AWT Frame:** Built using raw AWT for maximum performance and low memory footprint.
- **Game Loop:** Utilizes a custom `Runnable` loop with manual double-buffering (`BufferedImage`) to eliminate flickering.
- **Polled Input:** Uses `java.awt.Robot` to sample screen data globally without requiring window focus.
- **Optimized Polling:** Efficiency logic ensures CPU usage is minimized when the mouse is stationary.

---
*Created by [tkenji82](https://github.com/tkenji82)*
