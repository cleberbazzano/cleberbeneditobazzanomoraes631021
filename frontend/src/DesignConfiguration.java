/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // Azul oficial para o projeto SEPLAG
        seplag: {
          light: '#3b82f6',
          dark: '#1e40af',
          header: '#111827'
        }
      }
    },
  },
  plugins: [],
}