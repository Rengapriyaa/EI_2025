Design Patterns in Java

This repository demonstrates implementations of Design Patterns in Java, organized into three main categories:

Creational Patterns
Structural Patterns 
Behavioral Patterns 

Each example is written with a practical use case.

Project Structure
Design-Patterns/
├── creational/
│   ├── Singleton/
│   └── Factory/
├── structural/
│   ├── Adapter/
│   └── Decorator/
└── behavioural/
    ├── Observer/
    └── State/

Patterns & Use Cases

Creational Patterns

1. Singleton Pattern

Code Use Case: Provides a single global logger configuration across the system.

Real World: Database connection pools, configuration managers, caching systems.

2. Pattern

Code Use Case: Simplifies creation of objects (like roles or items) without exposing instantiation logic.

Real World: Payment system that generates CreditCard/UPI/PayPal objects depending on input.

Structural Patterns

1. Adapter Pattern

Code Use Case: Converts an Aadhar object into a VoterID object so two incompatible systems can work together.

Real World: Plug adapters, integrating old APIs with new services, or adapting third-party libraries.

2. Decorator Pattern

Code Use Case: In a restaurant, roles like Chef, Waiter, Cashier can be dynamically enhanced with bonus or seniority allowances without modifying their base classes.

Real World: Adding extra features (e.g., pizza toppings, premium subscription add-ons).

Behavioral Patterns

1. Observer Pattern

Code Use Case: A TodoList notifies observers when tasks are added or updated.

Real World: Event notification systems (news alerts, stock updates, UI listeners).

2. State Pattern

Code Use Case: An Order or Task changes its behavior depending on state (Created → In Progress → Completed).

Real World: Workflow engines, ticketing systems, traffic light controllers.

How to Run

Clone the repository:

git clone https://github.com/Rengapriyaa/Design-Patterns.git
cd Design-Patterns/src


Compile all files:
javac */*.java */*/*.java


Run a specific example:

java structural.decorator.RestaurantApp
java behavioural.observer.App
