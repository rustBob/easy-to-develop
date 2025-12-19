# Nova Admin - Vue 3 Dashboard

Nova Admin is a modern, responsive, and feature-rich backend management system built with Vue 3, Vite, and TailwindCSS. It serves as a comprehensive example of a modern frontend application, showcasing best practices in component design, state management, and API integration.

This project was migrated from a React codebase, and you can find detailed notes on the migration process in `MIGRATION_NOTES.md`.

## Features

- **Dashboard**: An overview of key metrics and analytics, featuring charts powered by ECharts.
- **User Management**: A table-based interface for managing users, with support for searching, filtering, and pagination.
- **AI Assistant**: A chat interface powered by Google Gemini, allowing users to interact with an AI assistant.
- **Settings**: A form-based interface for managing user profiles and application settings.
- **Responsive Design**: The layout is fully responsive and adapts to different screen sizes.

## Tech Stack

- **Framework**: [Vue 3](https://vuejs.org/) with the Composition API and `<script setup>`
- **Build Tool**: [Vite](https://vitejs.dev/)
- **Styling**: [TailwindCSS](https://tailwindcss.com/)
- **UI Library**: [Element Plus](https://element-plus.org/)
- **Routing**: [Vue Router](https://router.vuejs.org/)
- **Charting**: [ECharts](https://echarts.apache.org/) via [vue-echarts](https://github.com/ecomfe/vue-echarts)
- **AI**: [Google Gemini](https://ai.google.dev/)

## Project Setup

### 1. Clone the repository

```sh
git clone <repository-url>
cd campus-shuttle-admin
```

### 2. Install dependencies

```sh
npm install
```

### 3. Set up environment variables

Create a `.env` file in the root of the project and add your Google Gemini API key:

```
VITE_API_KEY="YOUR_API_KEY_HERE"
```

### 4. Run the development server

```sh
npm run dev
```

## Project Structure

```
src
├── assets
├── components  # Reusable Vue components (e.g., Header, Sidebar, StatCard)
├── pages       # Page-level components for each route
├── router      # Vue Router configuration
├── services    # API services (e.g., geminiService.ts)
├── styles      # Global styles and TailwindCSS configuration
└── types.ts    # TypeScript type definitions
```

## Available Scripts

- `npm run dev`: Starts the development server with hot-reloading.
- `npm run build`: Compiles and minifies the application for production.
- `npm run preview`: Serves the production build locally for previewing.
- `npm run lint`: Lints the codebase using ESLint.
