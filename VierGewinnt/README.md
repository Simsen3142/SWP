# VierGewinnt2
To be executed, the main Method of MainController in package controller needs to be executed
Several Patterns were used
Singletons
Observers
And a Command Pattern

Singleton: 
MainFrame: Reason: Easy Accessibility and no need to create more instances - is being removed for Single Player

Observer: 
SpielObserver - SpielHandler:
For handling game events
UserActionObserver - UserActionHandler:
For handling of user action events
Zug Observer - Zug:
For handling undo and redo actions

Command:
Zug:
Zug is the Object - the undo and the redo stack are included in Zug as well, because Observers are used to handle different use cases

Multiplayer (on 2 different devices) is coming soon
