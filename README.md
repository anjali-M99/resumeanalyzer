AI Based Resume Analyzer

**Overview**
The AI‑Powered Resume Analyzer is a Spring Boot + AI application designed to automatically parse resumes, extract structured information, and provide intelligent insights such as skill matching, experience evaluation, and role suitability. It helps recruiters quickly identify candidates who are most suitable for a given role.

**Features**
  Resume Upload - Candidates can upload their resumes(pdf/docx) for specific JobIds.
  Resume Parsing - Extracts text from PDF/DOCX resumes.
  AI Analysis - Uses AI models to understand whether candidate good fits or not assigning ATS score       and suggestion for recruiters.
  Resume Analysis - Recruiters can check who the eligible & good fit candidates for the Job.
  Secure Authentication: JWT‑based login and role management for admin.
  Database Integration: Stores uploaded resume & metadata, parsed resumes and analysis results.
  REST APIs: Exposes endpoints for uploading resumes and retrieving analysis.
  Kafka - Once a resume is uploaded, a Kafka consumer triggers the AI model to generate ATS scores      and suggestions.

 **Tech Stack**
Backend: Java, Spring Boot, Spring Security, Spring AI
AI model- google/gemma-3-27b-it
Database- MySQL
Authentication - JWT
Message passing - Kafka
Build Tool: Maven

**Installation & Setup**
Clone the repo -
Configure database in application.properties.
Add 256 Bits SECRET_KEY for JWT-(in JWTHelper)
Add Access key for Spring AI in application.properties
Run kafka
Run maven build
Start Spring boot application

**API Endpoints**
 1. POST **UploadResume** (path- http://localhost:8080/resume/user/uploadResume)
     User can upload their Resume along with jobId for which they are applying.
 2. POST **Login** → (http://localhost:8080/resume/auth/login?username=admin&password=admin-access)
     Login and get JWT
 3. GET **Run Analysis** (http://localhost:8080/resume/ai/run-analysis?yrExp=4&jId=AI-12)
      This api for Admin they need to pass JWT token to get the list of candidate for required job and experience.
 4. POST **Fetch User Specific Analysis** (http://localhost:8080/resume/ai/analysis-resume?mailId=**@gmail.com)
     This is to get the response for any specific job role, admin will pass job description in request to check eligibility of the user.
 5. GET **ParseFile** (http://localhost:8080/resume/user/extractFileData?mailId=**@gmail.com&jobId=AI-12)
    No need to run this here kafka consumer will run it internally.

**Workflow**
1. Upload
  A candidate uploads their resume (PDF/DOCX) via the application.
  The Kafka producer publishes a message indicating a new resume has been uploaded.

2. Consume & Store
  The Kafka consumer receives the message.
  It converts the uploaded MultipartFile into a byte[]. The byte[] is stored in the database along with metadata.
  The consumer then passes the resume file to a parser (Apache Tika). The parser extracts structured text and stores it in the database for further analysis.

3. AI Analysis
   Once parsing is complete, the system calls the AI model (google/gemma-3-27b-it). The model reads the structured resume data and generates:
   ATS Score (resume–job match score) and suggestions for recuriters, then updates it in the database.

4. Recruiters can view a summary of each candidate, including match score and AI suggestions, to quickly identify the best‑fit applicants.

👩‍💻 **Author**
Developed by Anjali  
Backend Engineer | AI Enthusiast
