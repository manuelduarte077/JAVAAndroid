# SOLID Principles Applied to Android Java App

This project demonstrates the application of SOLID principles to an Android Java application that fetches and displays posts and comments from a REST API.

## SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)
Each class has a single responsibility:
- `PostRepository`: Handles only post data operations
- `CommentRepository`: Handles only comment data operations
- `NetworkModule`: Handles only network configuration
- `PostListPresenter`: Handles only presentation logic for post list
- `PostDetailPresenter`: Handles only presentation logic for post details

### 2. Open/Closed Principle (OCP)
The code is open for extension but closed for modification:
- `NetworkModule` can be extended to support different base URLs without modifying existing code
- Repository interfaces allow for different implementations without changing client code

### 3. Liskov Substitution Principle (LSP)
Subtypes can be substituted for their base types:
- `PostRepository` implements `IPostRepository`
- `CommentRepository` implements `ICommentRepository`
- Activities implement their respective presenter view interfaces

### 4. Interface Segregation Principle (ISP)
Interfaces are specific to client needs:
- `PostListView` interface has only methods needed by MainActivity
- `PostDetailView` interface has only methods needed by PostDetailActivity
- `PostRepositoryCallback` provides a focused callback interface

### 5. Dependency Inversion Principle (DIP)
High-level modules depend on abstractions:
- Activities depend on repository interfaces, not concrete implementations
- Presenters depend on view interfaces, not concrete activities
- `ServiceLocator` provides dependencies through interfaces

## Project Structure

- **api**: Network-related classes
  - `ApiService`: Interface for API endpoints
  - `NetworkModule`: Creates and provides API services
  
- **di**: Dependency injection
  - `ServiceLocator`: Simple dependency injection container
  
- **model**: Data models
  - `Post`: Represents a post entity
  - `Comment`: Represents a comment entity
  
- **presenter**: Presentation logic
  - `PostListPresenter`: Handles post list presentation logic
  - `PostDetailPresenter`: Handles post detail presentation logic
  
- **repository**: Data access
  - `IPostRepository`: Interface for post operations
  - `PostRepository`: Implementation of post operations
  - `ICommentRepository`: Interface for comment operations
  - `CommentRepository`: Implementation of comment operations

- **adapter**: UI adapters
  - `PostAdapter`: Adapter for displaying posts
  - `CommentAdapter`: Adapter for displaying comments

- **Activities**: UI components
  - `MainActivity`: Displays list of posts
  - `PostDetailActivity`: Displays post details and comments

## Benefits of SOLID Implementation

1. **Maintainability**: Each class has a single responsibility, making the code easier to maintain
2. **Testability**: Dependencies are injected and interfaces are used, making testing easier
3. **Flexibility**: The code is open for extension, making it easier to add new features
4. **Reusability**: Components are decoupled, making them reusable in other parts of the app
5. **Scalability**: The architecture supports growth and changes without major refactoring
