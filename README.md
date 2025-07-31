# Quarkus MCP Server Example

A comprehensive example demonstrating how to build a **Model Context Protocol (MCP) server** using **Quarkus** that exposes REST API operations as AI tools. This enables Large Language Models like Claude to interact with your application data through natural language using the **Goose CLI**.

## 🎯 What This Example Demonstrates

This project showcases the integration of three powerful technologies:

- **Quarkus**: Supersonic, subatomic Java framework for building modern applications
- **MCP (Model Context Protocol)**: Open standard for connecting AI assistants to data sources and tools
- **Goose CLI**: AI-powered development assistant that can interact with MCP servers

## 📋 Table of Contents

- [What is MCP?](#what-is-mcp)
- [What is Quarkus?](#what-is-quarkus)  
- [What is Goose?](#what-is-goose)
- [Solution Architecture](#solution-architecture)
- [Prerequisites](#prerequisites)
- [Running the Example](#running-the-example)
- [Usage Examples](#usage-examples)

## 🔗 What is MCP?

**Model Context Protocol (MCP)** is an open standard that enables AI assistants to securely connect to and interact with various data sources and tools. Instead of building custom integrations for each AI assistant, MCP provides a universal protocol for:

- **Tool Discovery**: AI assistants can discover what tools are available
- **Tool Execution**: Safely execute operations on external systems
- **Data Access**: Retrieve and manipulate data from various sources
- **Standardization**: One implementation works with any MCP-compatible AI assistant

### MCP Benefits
- **Interoperability**: Works with any MCP-compatible AI assistant
- **Security**: Controlled access to external resources
- **Extensibility**: Easy to add new tools and capabilities
- **Standardization**: No need for custom integrations per AI assistant

## ⚡ What is Quarkus?

**Quarkus** is a Kubernetes-native Java framework designed for modern application development:

- **Fast Startup**: Sub-second startup times
- **Low Memory Usage**: Optimized for containerized environments
- **Developer Joy**: Live reload, unified configuration, and great tooling
- **Cloud Native**: Built for Kubernetes, serverless, and microservices
- **Standards Based**: Uses familiar Java standards (JAX-RS, CDI, JPA)

### Quarkus for MCP
Quarkus is ideal for MCP servers because:
- **Quick Development**: Fast feedback loop with live reload
- **Lightweight**: Minimal resource usage for tool servers
- **Native Compilation**: Ultra-fast startup with GraalVM
- **Rich Ecosystem**: Extensive extensions for database, REST clients, etc.

## 🦆 What is Goose?

**Goose** is an AI-powered development assistant CLI that helps developers by:

- **Natural Language Interface**: Interact with your development environment using plain English
- **MCP Integration**: Connects to MCP servers to access external tools and data
- **Context Awareness**: Understands your project structure and codebase
- **Tool Execution**: Can execute commands, modify files, and interact with APIs
- **Multi-Model Support**: Works with various LLMs including Claude, GPT, etc.

### Goose with MCP
When combined with MCP servers, Goose can:
- Query databases through natural language
- Interact with REST APIs without manual curl commands
- Perform complex operations by combining multiple tools
- Provide intelligent insights based on your application data

## 🏗️ Solution Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           MCP Integration Flow                               │
└─────────────────────────────────────────────────────────────────────────────┘

    ┌─────────────┐    MCP Protocol     ┌─────────────────┐    REST API    ┌──────────────┐
    │             │   (JSON-RPC over    │                 │   (HTTP/JSON)  │              │
    │    Goose    │◄───────────────────►│   Quarkus MCP   │◄──────────────►│   Quarkus    │
    │     CLI     │     STDIO/SSE)      │     Server      │                │ Applications │
    │             │                     │                 │                │      MS      │
    └─────────────┘                     └─────────────────┘                └──────────────┘
           │                                      │                               │
           │                                      │                               │
    ┌──────▼──────┐                        ┌─────▼─────┐                 ┌───────▼───────┐
    │             │                        │           │                 │               │
    │   Claude    │                        │ MCP Tools │                 │  PostgreSQL   │
    │     LLM     │                        │           │                 │   Database    │
    │             │                        │ • list    │                 │               │
    └─────────────┘                        │ • get     │                 └───────────────┘
                                           │ • filter  │
                                           │ • add     │
                                           └───────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                              Data Flow                                      │
└─────────────────────────────────────────────────────────────────────────────┘

1. User asks Goose: "Show me all applications from Spain"
                    ↓
2. Goose sends MCP request to discover available tools
                    ↓
3. MCP Server exposes tools: list_applications, get_applications_by_country, etc.
                    ↓
4. Goose calls get_applications_by_country("Spain") tool
                    ↓
5. MCP Server makes REST call to Applications MS
                    ↓
6. Applications MS queries PostgreSQL database
                    ↓
7. Results flow back through the chain to Goose
                    ↓
8. Goose presents results to user in natural language
```

### Component Responsibilities

| Component | Purpose | Technology |
|-----------|---------|------------|
| **Goose CLI** | AI assistant interface, natural language processing | Go, LLM integration |
| **Quarkus MCP Server** | Protocol bridge, tool definitions | Quarkus, MCP protocol |
| **Quarkus Applications MS** | Business logic, data operations | Quarkus, JAX-RS, Hibernate |
| **PostgreSQL** | Data persistence | Relational database |

## Prerequisites

Before running this example, ensure you have the following installed:

- **Java 21**: Required for Quarkus applications
- **Maven**: Build tool for the Java projects
- **Podman/Docker**: Container runtime for PostgreSQL database
- **Goose CLI**: AI assistant tool ([installation guide](https://github.com/square/goose))

### Installing Goose

```bash
# Using Go (requires Go 1.21+)
go install github.com/square/goose@latest

# Or download binary from releases
# https://github.com/square/goose/releases
```

## 🚀 Running the Example

### Step 1: Build the Project

```console
mvn clean package
```

### Step 2: Start PostgreSQL Database

```console
podman run -d --name db-server \
  -e POSTGRES_USER=test \
  -e POSTGRES_PASSWORD=test \
  -e POSTGRES_DB=applications \
  -p 5432:5432 \
  postgres:16
```

### Step 3: Start the Applications Microservice

```console
cd quarkus-applications-ms && mvn quarkus:dev
```

The applications service will start on `http://localhost:8080`

### Step 4: (Optional) Add Sample Data

```console
curl -i -X POST \
  -F "name=Angel" \
  -F "surname=Miralles" \
  -F "personalId=12345R" \
  -F "country=Spain" \
  http://localhost:8080/application.html

curl -i -X POST \
  -F "name=Juan" \
  -F "surname=Garcia" \
  -F "personalId=31234R" \
  -F "country=Spain" \
  http://localhost:8080/application.html
  
curl -i -X POST \
  -F "name=Pedro" \
  -F "surname=Rodriguez" \
  -F "personalId=54321R" \
  -F "country=Germany" \
  http://localhost:8080/application.html
```

### Step 5: Start Goose with MCP Server

```console
goose session --with-extension="java -jar quarkus-mcp-server/target/quarkus-mcp-server-1.0.0-SNAPSHOT.jar"
```

## 💡 Usage Examples

Once you have Goose running with the MCP server, you can interact with your application data using natural language:

### Basic Queries
```
> Show me all applications in the system
> List applications from Spain
> Get application with ID 1
```

### Filtered Queries
```
> Show me all applications from France with COMPLETED status
> List all STARTED applications
> Find applications from Germany
```

### Data Management
```
> Add a new application for John Doe from USA with ID 67890
> Create an application for Maria Garcia from Mexico
```

### Available MCP Tools

The MCP server exposes these tools to Goose:

| Tool Name | Description | Parameters |
|-----------|-------------|------------|
| `list_applications` | Lists all applications | None |
| `get_application_by_id` | Get specific application | `id: Long` |
| `get_applications_by_country` | Filter by country | `countryName: String` |
| `get_applications_by_country_and_status` | Filter by country and status | `countryName: String`, `status: ApplicationStatus` |
| `add_application` | Create new application | `application: Application` |

### Application Status Values
- `STARTED`
- `PREPROCESSED` 
- `PROCESSED`
- `COMPLETED`
- `CANCELED`
- `ERROR`

## 🔧 Development

### Development Mode
```bash
# Start applications service in dev mode (hot reload)
cd quarkus-applications-ms && mvn quarkus:dev

# Package MCP server for testing
cd quarkus-mcp-server && mvn package
```

### Project Structure
```
quarkus-mcp-server-example/
├── quarkus-applications-ms/           # REST API microservice
│   ├── src/main/java/me/amiralles/applications/
│   │   ├── boundary/ApplicationsResource.java    # REST endpoints
│   │   ├── entity/Application.java               # JPA entity
│   │   └── control/ApplicationExceptionMapper.java
│   └── src/main/resources/application.properties
├── quarkus-mcp-server/                # MCP server
│   ├── src/main/java/me/amiralles/mcp/
│   │   ├── McpApplicationsServer.java            # MCP tools definition
│   │   ├── client/ApplicationsClient.java        # REST client
│   │   └── model/Application.java                # Data models
│   └── src/test/java/
└── deployment/docker-compose/         # Monitoring stack
```

## 🔗 References

- [Model Context Protocol (MCP)](https://modelContextProtocol.io/)
- [Quarkus Framework](https://quarkus.io/)
- [Quarkus MCP Extension](https://github.com/quarkiverse/quarkus-mcp-server)
- [Goose CLI](https://github.com/square/goose)
- [Claude AI](https://claude.ai/)

## 📄 License

This project is provided as an example for educational purposes.

