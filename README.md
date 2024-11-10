# Research-Paper-Reference-System
An application built for students to help them search for and cite research articles.
## Description

Search and Data Handling: 
The program will web scrape the provided API to get data on relevant research papers. Using this data, it will analyze and summarize the paper, and determine it’s relevance to the research topic and its relationship to other similar research papers. 

Graph Visualization:  
A directional graph will be used to display all the research articles associated with the topic searched. The nodes of the graph will represent a research paper. The edges of the graph, which will be directional, will represent citations (e.g. A -> B means A cites B). 

User Interaction:  
Using Java Swing, the program allows users to enter research topics and see relevant papers in the form of a graph. By clicking and dragging the graph, users can get a clearer view. Users are provided with the option to click a node for detailed information. They are also able to login in to save and check their search history and bookmark important papers for later access. 

User Stories:
- As a student writing a paper on “Should Ontario School Board need to ban phones in classrooms”, I am unsure about where to start. I enter some keywords into the search bar, which generates a graph displaying the 10 most relevant articles. Articles that cite each other (and are thus more related) are visualized, allowing me to easily identify which group of articles are most relevant to my topic, and which articles may be used to further extend my research in a particular direction. [team story] 
- As a student, I want to be able to sign up/log in to the research paper database in order to be able to search for papers that I can use for my own research.
- As a student writing a research paper on Alzheimer's disease, I want to be able to type in my topic in the search bar and be shown other research papers relevant to my topic.
- As a student writing a research paper on ethics in machine learning, I want to be able enter my topic and quickly see what research article in the system is the most relevant to my topic and see what other articles it cites in order to ensure that I have the most relevant and trustworthy article.
- As a student who loves Canadian geese, I want to be able to search for and see the 10 most relevant articles about the migration of Canadian geese so that I can get a general understanding about this topic.
- As a student writing a research paper on the role of pets in reducing stress, I want to be able to enter my topic into the search bar and be provided with a summarization of the most relevant paper.
- As a student writing a research paper on the evolution of video games, I want to be able to enter my topic into the search bar and click on any node in the given graph in order to view more detailed info about the paper, such as the link to it or the date of publication.
- As a student writing a research paper on the hippo pathway, I want to be able to use the generated directed graph to see which paper cite which. This way, I can see which paper is the most recent (as it cites most of the other papers) or which paper contains the most fundamental information (as most of the other papers cite it), and then use the one that corresponds most with what I want in my own research paper.
  
## Authors
1. Joyee Jin
2. Ashley Bi
3. Amy Sun
4. Bowen Zhang
5. Elizabeth Liu

