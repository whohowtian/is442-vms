start cmd.exe /k "cd backend && mvnw spring-boot:run"
start cmd.exe /k "cd frontend && npm run dev"
start chrome http://localhost:8080/swagger-ui/index.html
start chrome http://localhost:5173