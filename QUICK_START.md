# Quick Start Guide

## Option 1: Using IntelliJ IDEA (Recommended)

1. **Open the project in IntelliJ IDEA**
   - File → Open → Select `C:\Users\Noor\IdeaProjects\MarkDown`
   - IntelliJ will automatically detect it as a Maven project

2. **Let IntelliJ download dependencies**
   - Wait for the Maven import to complete (see progress bar at bottom)
   - IntelliJ will download all required dependencies

3. **Run the application**
   - Find `MarkdownNotesApplication.java` in the Project Explorer
   - Right-click on the class
   - Select "Run 'MarkdownNotesApplication'"

4. **Access the API**
   - Open browser: `http://localhost:8080`
   - API is now ready to use!

---

## Option 2: Using Maven Command Line

### Install Maven (if not installed)
Download from: https://maven.apache.org/download.cgi

Or use Maven Wrapper (included):

**Windows:**
```bash
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

---

## Option 3: Build JAR and Run

### Using IntelliJ Maven Tool Window:
1. Open Maven tool window (View → Tool Windows → Maven)
2. Expand Lifecycle
3. Double-click `clean` then `install`
4. After build completes, find JAR in `target/` folder
5. Run: `java -jar target/markdown-notes-1.0.0.jar`

---

## Verify Installation

Once running, visit:
```
http://localhost:8080
```

You should see:
```json
{
  "application": "Markdown Note-taking API",
  "version": "1.0.0",
  ...
}
```

---

## Next Steps

1. Read `README.md` for full documentation
2. Check `API_TESTING_GUIDE.md` for testing examples
3. Use the sample markdown files (`sample-note.md`, `meeting-notes.md`) for testing uploads

---

## Troubleshooting

### Port 8080 already in use?
Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Maven issues?
- Use IntelliJ's built-in Maven (recommended)
- Or download Maven from https://maven.apache.org/

### Can't find dependencies?
- Check internet connection
- IntelliJ: Right-click `pom.xml` → Maven → Reload Project

---

## Project Structure Created

✅ Complete Spring Boot REST API  
✅ Grammar checking with LanguageTool  
✅ Markdown to HTML conversion  
✅ File upload support  
✅ H2 Database integration  
✅ Complete CRUD operations  
✅ Error handling  
✅ Sample markdown files  
✅ Comprehensive documentation  

**You're all set!** 🚀

