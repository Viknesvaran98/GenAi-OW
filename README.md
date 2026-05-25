📌 GenAi CLI Tool

A simple Java-based command-line assistant that can respond to basic prompts and open folders or system settings directly from terminal commands.

🚀 Features
Interactive command-line interface
Basic AI-style responses (rule-based)
Open system folders using simple commands
Open OS settings (Windows, macOS, Linux)
Recursive folder search inside user directory
Cross-platform support

🛠️ Requirements
Java JDK 8 or higher
Terminal or command prompt

prompt
📦 How to Run
1. Compile the program
javac GenAi.java
2. Run the program
java GenAi
💡 How to Use

After running the program:

🔹 Chat mode

Just type anything:

hello
how are you
time
🔹 Open folders or settings

Use the open command:

open Downloads
open Documents
open settings

🧠 Example Output
Ask me anything (type 'end' to quit):
> hello
AI: Hello! How can I assist you today?

> time
AI: I don't have a clock, but you can check your system time!

> open Downloads
Opened: C:/Users/YourName/Downloads
⚙️ Supported Commands
Command	Description
hello / hi	Simple greeting response
how are you	Status reply
time	Fake time response
open <folder>	Opens folder or searches in user directory
open settings	Opens system settings
end	Exit program

> 🧩 Notes
Folder search scans inside your user directory, so large drives may take time.
Settings command depends on OS support.
This is a lightweight rule-based AI, not a real LLM.
