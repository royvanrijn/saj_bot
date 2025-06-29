# SAJ bot
Does the following:

Given some hyperparameters, it calculates the ideal re-delivery of electricity to the net, to minimize cost and optimize returns.

Out of the box, the SAJ H2 Home Battery only supports a couple of operating modus. Those are Self-consuming, Time-of-Use, with a schedule. This bot takes into account the selected energy prices (for import and export), predicted weather (using external API) and optimizes the battery levels. It takes into consideration how much is needed at any moment in time and will load when energy prices are negative, sells when the price is optimal. It even leaves some energy in the battery when it's predicted the user need some during the night. Can be self-learning/optimizing.

Main things that are needed to implement:
- API that reads out and communnicates with H2 (can be based on https://github.com/stanus74/home-assistant-saj-h2-modbus)
- Reads Weather API based on the location in the config
- Reads electricity prices for the current day
- "Brain" that contains the algorithm that determines how the battery operates (schedules charge/discharge accordingly)
- Storage of all sensor data, weather data and prices to optimize the algorithm in the future
- Guide how to run on Umbrel
- Built in Java, using Maven and Spring Boot, simple web UI, option to access/download all data
