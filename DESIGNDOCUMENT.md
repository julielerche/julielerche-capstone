# TASK WARRIOR

### Problem Statement
I start every day with a to-do list, in the past I have used apps to track my progress but none of them have enough incentive to keep me around for very long versus pen and paper. My goal is to create a gamified to-do list that includes routine tasks that you do every day (dailies), chores that you do every once and a while but repeatedly (chores), and unique one off tasks that disappear once completed (To-Dos). On completion of a task, the user randomly receives an amount of gold that they can use to buy items to restore stamina, health, and mana bars. These bars are used in combat to defeat creatures and level up.

### User Stories
1. As a user, I need to create a task and assign it to Dailies, Chores, or To-Dos. ✔
2. As a user, I need to mark a task as completed for the day. ✔
3. As a user, I want to update a task to change name or difficulty, or assign to a different category. ✔
4. As a user, I want to delete tasks that aren't relevant anymore. ✔
5. As a user, I want to start a new day manually to reset tasks. ✔
6. As a user, I want to spend gold at the store to get items. ✔
7. As a user, I want to see items in my inventory with descriptions. ✔
8. As a user, I want to spend stamina to attack monsters. ✔
9. As a user, I want to spend mana to use magic. ✔
10. As a user, I need to save my data. ✔
11. As a user, I want to change my display name. ✔

### Stretch Goals
1. As a user, I want to have different attacks to choose from.
2. As a user, I want multiple spells to choose from.
3. As a user, I want multiple potions with different effects. ✔
4. As a user, I want to see animations of the attacks.
5. As a user, I want to gain EXP which allows for more moves.
6. As a user, I want the day to reset at a set UTC time.

### UML Diagram
![Class Diagram](Class Diagram.png)

## Tables

### User Table
| UserId (Partition Key) | displayName | dailies      | chores       | todos        | inventory    | stats                                    |
|------------------------|-------------|--------------|--------------|--------------|--------------|------------------------------------------| 
| String                 | String      | String(json) | String(json) | String(json) | String(json) | Number Set (health, stamina, mana, gold) |

### Assets Table
| assetType (Partition Key) (GSI key) | assetId (Sort Key) | name | description | healthOrCost (GSI Key) |
|-------------------------------------|-------------|--------------|--------------|------------------------|
| String (Potion, Monster, Item)      | Number (unique per assetType) | String | String | Number                 |
*HealthOrCost as a GSI Key allows to getting Monsters of a certain health or items that are affordable to the user.*

### Encounter Table
| UserId (Partition Key) | monsterList |
|------------------------|-------------|
| String                 | List<Asset> | 

# API
## API Interactable Objects
### User Object
- UserId (String) (not changable)
- DisplayName (String) (changable)
- Dailies (List of Tasks)
- Chores (List of Tasks)
- ToDos (List of Tasks)
- Inventory (List of Assets)
- Health (Integer)
- Stamina (Integer)
- Mana (Integer)
- Gold (Integer)

### Task Object
- TaskType (enum of DAILY, CHORE, TODO)
- TaskName
- Difficulty (enum of EASY, MEDIUM, HARD)
- Completed (Boolean)

### Attack Object
- AttackPower (int)
- StaminaNeeded (int)
- Target (int)

### Spell Object
- AttackPower (int)
- ManaNeeded (int)

### Encounter Object
- UserId (string)
- List<Asset> MonsterList

### Asset Object
- AssetType (String)
- AssetId (Integer)
- Name (String)
- Description (String)

### Item Object inherits from Asset
- AssetType (String) 
- AssetId (Integer)
- Name (String)
- Description (String)
- Cost (Integer)

### Potion Object inherits from Asset
- AssetType (String)
- AssetId (Integer)
- Name (String)
- Description (String)
- Cost (Integer)

### Monster Object inherits from Asset
- AssetType (String)
- AssetId (Integer)
- Name (String)
- Description (String)
- StartingHealth (Integer)
- CurrentHealth (Integer)
- AttackPower (Integer)

## API Endpoints

## Post Endpoints
## CreateUserLambda
Creates a new user in the User table with blank data.
### CreateUserRequest
```
userId // userId from Login to create save data for
displayName // display name to be saved with the user
```
### CreateUserResponse
```
user // object with default data returned from creation
```

## CreateAssetLambda
Creates a new asset in the Asset table with correct data.
### CreateAssetRequest
```
asset JSON data:
assetType // enum of POTION, ITEM, MONSTER
assetId // unique identifier within each Type
name // String of the name 
description // String of the description of the asset
healthOrCost // Integer of either the cost or health of asset
attackPower // optional int of monster damage
```
### CreateAssetResponse
```
asset // either an item or monster object that was created from the request
```
## CreateEncounterLambda
Creates a new encounter in the encounter table with random data.
### CreateEncounterRequest
```
userId // userId from Login to create encounter data for
```
### CreateEncounterResponse
```
encounter // encounter model object with random monsters
```


# Put Endpoints

## AddTaskToUserLambda
Creates a new task and saves it within existing user.
### AddTaskToUserRequest
```
userId // the user to create the task in
task // json data of the task
```
### AddTaskToUserResponse
```
user // the updated user
```

## AddAssetToUserLambda
Gets an asset from the table and saves it within existing user.
### AddAssetToUserRequest
```
userId // the user to create the task in
assetType // string of the asset type
assetId // int of the assetid
```
### AddAssetToUserResponse
```
user // user with updated inventory
```

## UpdateUserLambda
Updates the user table with new user data.
### UpdateUserRequest
```
userId // the user id that we need to update in the table
name // the new name to update the user with
```
### UpdateUserResponse
```
user // the updated user object
```

## UpdateTaskLambda
Updates the user table with new task data.
### UpdateTaskRequest
```
userId // the user id that we need to update in the table
task  // the task object that is going to be updated
newName // optional new name
newType // optional new type
newDifficulty // optional new difficulty
```
### UpdateTaskResponse
```
user // the updated user object
```

## MarkTaskAsCompletedLambda
Updates the user table with completed task data.
### MarkTaskAsCompletedRequest
```
userId // the user id that we need to update in the table
task  // the task object that is going to be completed
```
### MarkTaskAsCompletedResponse
```
task // the updated task object
gold // gold earned from task being completed
```

## StartNewDayLambda
Sets all tasks to not completed. User loses health for uncompleted daily tasks.
### StartNewDayRequest
```
userId // the user id that we need to update in the table
```
### StartNewDayResponse
```
user // the updated user object
```

## AttackMonsterLambda
Uses an attack to attack the current monsters. Needs stamina from user.
### AttackMonsterRequest
```
userId // the user id to get the encounter from dao
attack // the attack to use on one monster
```
### AttackMonsterResponse
```
encounter // the encounter model with updated values
goldEarned // optional gold earned from defeating monsters
```

## SpellMonsterLambda
Uses a spell to attack the current monsters. Needs mana from user.
### SpellMonsterRequest
```
userId // the user id to get the encounter from dao
spell // the spell to use on all monsters
```
### SpellMonsterResponse
```
encounter // the encounter model with updated values
goldEarned // optional gold earned from defeating monsters
```

## MonsterAttackLambda
Current monsters attack the user, user loses health.
### MonsterAttackRequest
```
userId // the user id to get the encounter from dao
```
### MonsterAttackResponse
```
user // the updated user object
```

# Get Endpoints

## GetUserLambda
Gets the user from the user table.
### GetUserRequest
```
userId // the userId that is used at the table key.
```
### GetUserResponse
```
user // the user object requested
```

## GetEncounterLambda
Gets the encounter from the encounter table.
### GetEncounterRequest
```
userId // the userId that is used as the table key.
```
### GetEncounterResponse
```
encounter // the model encounter gotten from the table
```

## GetUserInventoryLambda
Gets the user's inventory from the user table.
### GetUserRequest
```
userId // the userId that is used at the table key.
```
### GetUserResponse
```
items // List<Assets> of user inventory
```

## GetUserTasksLambda
Gets the requested tasks from the user table.
### GetUserTasksRequest
```
userId // the userId that is used as the table key.
taskType // enum of DAILY, CHORE, or TODO
```
### GetUserTasksResponse
```
tasks // list<tasks> from the table
```

## GetAllOfAssetTypeLambda
Gets the requested assets of a certain type from the asset table.
### GetAllOfAssetTypeRequest
```
assetType // enum of POTION, ITEM, or MONSTER
assetType2 // optional second enum (for the shop to get all potions and items)
```
### GetAllOfAssetTypeResponse
```
assetList // a list of all assets of a certain type
```

## GetAffordableAssetsLambda
Gets the requested assets that are affordable to the user.
### GetAffordableAssetsRequest
```
userId // user id to get the gold count for 
```
### GetAffordableAssetsResponse
```
assetList // a list of all assets of a certain type
```

# Delete Endpoints
## DeleteTaskLambda
Deletes the task from the user's task list, then saves user data without task. Throws error if task doesn't exist.
### DeleteTaskRequest
```
userId // userId for the table to check
task // json data of the task to delete
```
### DeleteTaskResponse
```
task // the deleted task
```
## UseItemLambda
Uses an item in the user's inventory, deleting the item and using its effect.
### UseItemRequest
```
userId // userId for the table to check
asset // json data of the asset to use
```
### UseItemResponse
```
user // the updated user
```

## Sequence Diagram
![Sequence Diagram](GetTasksSequenceDiagram.png)
*Walkthrough of the GetTasksLambda Sequence Diagram*

## Mockup
![Concept Art for Frontend](Wireframe.png)
*Concept art for the Frontend application*