import Header from '../components/header';
import BindingClass from "../util/bindingClass";
/**
 * Logic needed for the view playlist page of the website.
 */
export default class ViewUser extends BindingClass {
    constructor(client, dataStore) {
        super();
        this.client = client;
        this.dataStore = dataStore;
        this.bindClassMethods(['clientLoaded', 'mount', 'addUserToPage', 'addTasksToPage', 'addStoreToPage',
            'addInventoryToPage', 'addStatsToPage', 'delete', 'markComplete', 'updateUserName', 'createNewTask', 
            'useItem', 'startNewDay', 'addGoldToPage', 'buyItem', 'updateTask'], this);
        this.dataStore.addChangeListener(this.addUserToPage);
        this.dataStore.addChangeListener(this.addGoldToPage);
        this.dataStore.addChangeListener(this.addStoreToPage);
        this.dataStore.addChangeListener(this.addTasksToPage);
        this.dataStore.addChangeListener(this.addInventoryToPage);
        this.dataStore.addChangeListener(this.addStatsToPage);
        //this.header = new Header(this.dataStore);
        console.log("viewUser constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        document.getElementById('user-name').innerText = "loading user ...";
        const user = this.dataStore.get('user');
        this.dataStore.set('gold', user.gold);
        this.dataStore.set('inventory', user.inventory);
        const store = await this.client.getStore();
        this.dataStore.set('store', store);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        
        //this.header.addHeaderToPage();
        this.clientLoaded();
        document.getElementById('nav-tabContent').addEventListener('click', this.delete);
        document.getElementById('nav-tabContent').addEventListener('click', this.markComplete);
        document.getElementById('nav-tabContent').addEventListener('click', this.updateTask);
        document.getElementById('updateUserName').addEventListener('click', this.updateUserName);
        document.getElementById('nav-new-day').addEventListener('click', this.startNewDay);
        document.getElementById('createTaskButton').addEventListener('click', this.createNewTask);
        document.getElementById('user-inventory').addEventListener('click', this.useItem);
        document.getElementById('store-page').addEventListener('click', this.buyItem);
    }

    /**
    * When user is updated in the datastore, updates user metadata on page
    */
    addUserToPage() {
        const user = this.dataStore.get('user');
        if (user == null) {
            return;
         }
        document.getElementById('user-name').innerText = user.displayName;
    }

    addGoldToPage() {
        const gold = this.dataStore.get('gold');
        if (gold == null) {
            return;
         }
        document.getElementById('gold-amount').innerText = gold + " Gold";
    }

    /**
     * adds user inventory to the page tab.
     */
    addInventoryToPage() {
        const inventory = this.dataStore.get('inventory');
        if (inventory == null) {
            return;
        }
        
        let inventoryHTML = '<div class="row row-cols-1 row-cols-md-3 g-4">';
        let assetCounter = 0;
        let asset;
        for (asset of inventory) {
            assetCounter++;
            inventoryHTML += `<div class="col">
                <div class="card h-100" style="width: 10rem;">
                    <img class="card-img-top" src="sprites/${asset.name}.png" alt="Card image cap">
                    <div class="card-body">
                    <h5 class="card-title">${asset.name}</h5>
                    <p class="card-text">${asset.description}</p>
                    <button data-assetName="${asset.name}" data-assetDescription="${asset.description}" data-assetType="${asset.assetType}" data-assetID="${asset.assetId}" class="button use-item">Use Item</button>
                    </div>
                    </div>
                    </div>
            `;
            // if (assetCounter % 2 == 0) {
            //     inventoryHTML += `</div>
            //     `;
            // }
        }
        inventoryHTML += `</div>`;
        document.getElementById('user-inventory').innerHTML = inventoryHTML;

    }

        /**
     * adds user stats to the progress bars.
     */
        addStatsToPage() {
            const user = this.dataStore.get('user');
            const health = user.health;
            if (health == null) {
                return;
            }
            document.getElementsByClassName('progress-bar bg-danger').item(0).setAttribute('aria-valuenow',health);
            document.getElementsByClassName('progress-bar bg-danger').item(0).setAttribute('style','width:'+Number(health)+'%');
    
            const mana = user.mana;
            if (mana == null) {
                return;
            }
            document.getElementsByClassName('progress-bar bg-info').item(0).setAttribute('aria-valuenow',mana);
            document.getElementsByClassName('progress-bar bg-info').item(0).setAttribute('style','width:'+Number(mana)+'%');
    
            const stamina = user.stamina;
            if (stamina == null) {
                return;
            }
            document.getElementsByClassName('progress-bar bg-success').item(0).setAttribute('aria-valuenow',stamina);
            document.getElementsByClassName('progress-bar bg-success').item(0).setAttribute('style','width:'+Number(stamina)+'%');
    
        }

    /**
     * Gets user tasks in each tab
     */
    addTasksToPage() {
        const user = this.dataStore.get('user');
        if (user == null) {
            return;
        }
        const dailies = user.dailies;
        if (dailies == null) {
            return;
        }
        const dailyTaskHTML = this.getTaskHTMLFromList(dailies);
        document.getElementById('daily-tasks').innerHTML = dailyTaskHTML;

        const chores = user.chores;
        if (chores == null) {
            return;
        }
        const choreTaskHTML = this.getTaskHTMLFromList(chores);
        document.getElementById('chore-tasks').innerHTML = choreTaskHTML;

        const todos = user.toDos;
        if (todos == null) {
            return;
        }
        const todoTaskHTML = this.getTaskHTMLFromList(todos);
        document.getElementById('todo-tasks').innerHTML = todoTaskHTML;
    }

    getTaskHTMLFromList(taskList) {
        let counter = 0;
        let taskHTML = '';
        let task;
        for (task of taskList) {
            const buttonId = task.taskType + counter;
            counter++;
            taskHTML += `
            <div class="card">
                <div class="container">
                    <div class="row">
                         <div class="col"> <strong>
                           ${task.taskName}
                         </strong></div>`
                        if (task.difficulty == "EASY") {
                            taskHTML += `<div class="col-2">
                            ★☆☆</div>`;
                        } else if (task.difficulty == "MEDIUM") {
                            taskHTML += `<div class="col-2">
                            ★★☆</div>`;
                        } else {
                            taskHTML += `<div class="col-2">
                            ★★★</div>`;
                        }
                        if (task.completed) {
                            taskHTML += `<div class="col-2">
                            ☒</div>`;
                        } else {
                            taskHTML += `<div class="col-2">
                            ☐</div>`;
                        }
                        taskHTML += `</div> <div class="row"> <div class="col">
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#${buttonId}" aria-expanded="false" aria-controls="${buttonId}"> Update Task </button>
                        </div>
                        <div class="col">
                        <button data-taskName="${task.taskName}" data-taskType="${task.taskType}" data-difficulty="${task.difficulty}" class="button delete-task">Delete</button>
                        </div>
                        <div class="col-5">
                        <button data-taskName="${task.taskName}" data-taskType="${task.taskType}" data-difficulty="${task.difficulty}" class="button complete-task">Mark Complete</button>
                        </div>
                    </div>
                    
                    
              
              <div class="collapse" id="${buttonId}">
              <div class="card">
              <form class="card-content" id="update-task-form">
                  <p class="hidden error" id="error-message"> </p>
                  <p class="form-field">
                  <sub> All fields are optional.</sub>
                      <label>New Task Name</label>
                      <input type="text" id="update-newTaskName-${buttonId}" placeholder="Clean Dishes" autofocus>
                  </p>
                  <div class="row">
                      <div class="col-6">
                          <label>New Task Type</label>
                  <select class="form-control form-control-sm" id="update-newTaskType-${buttonId}">
                      <option value=null> </option>
                      <option value="DAILY">Daily</option>
                      <option value="CHORE">Chore</option>
                      <option value="TODO">ToDo</option>
                    </select>
                  </div>
                  <div class="col-6">
                      <label>New Difficulty</label>
                    <select class="form-control form-control-sm" id="update-newTaskDifficulty-${buttonId}">
                      <option value=null> </option>
                      <option value="EASY">Easy</option>
                      <option value="MEDIUM">Medium</option>
                      <option value="HARD">Hard</option>
                    </select>
                  </div>
              </div>
              <p></p>
                  <p>
                  <div id="updateTaskListener">
                  <button data-buttonId=${buttonId} data-taskName="${task.taskName}" data-taskType="${task.taskType}" data-difficulty="${task.difficulty}" class="button update-task" id="updateTaskButton">Update Task</a>
                  </div>
                  </p>
              </form>
              <p class="hidden error" id="error-message-update"> </p>
          </div>
              </div>
                    
                </div>
            </div>
            `;
        }
        return taskHTML;
    }


    /**
         * when update button is clicked, updates tasks in tasklist.
         */
    async updateTask(e) {
        const updateTaskButton = e.target;
        if (!updateTaskButton.classList.contains("update-task")) {
            return;
        }

        updateTaskButton.innerText = "Updating...";
        const buttonId = updateTaskButton.dataset.buttonid;

        const errorMessageDisplay = document.getElementById('error-message-update');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        const nameId = "update-newTaskName-" + buttonId;
        const typeId = "update-newTaskType-" + buttonId;
        const difficultyId = "update-newTaskDifficulty-" + buttonId;
        const taskName = document.getElementById(nameId).value;
        const newTaskType = document.getElementById(typeId).value;
        const newTaskDifficulty = document.getElementById(difficultyId).value;

        const user = await this.client.updateTask(updateTaskButton.dataset.taskname, updateTaskButton.dataset.tasktype, updateTaskButton.dataset.difficulty, taskName, newTaskType, newTaskDifficulty, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('user', user);

    }
    /**
          * when remove button is clicked, removes task from tasklist.
          */
    async delete(e) {
        const deleteButton = e.target;
        if (!deleteButton.classList.contains("delete-task")) {
            return;
        }

        deleteButton.innerText = "Removing...";

        const errorMessageDisplay = document.getElementById('error-message-task');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const user = await this.client.removeTaskFromList(deleteButton.dataset.taskname, deleteButton.dataset.tasktype, deleteButton.dataset.difficulty, (error) => {
           errorMessageDisplay.innerText = `Error: ${error.message}`;
           errorMessageDisplay.classList.remove('hidden');
       });
       this.dataStore.set('user', user);
  }

  /**
          * when mark complete button is pushed, updates task.
          */
  async markComplete(e) {
    const completeButton = e.target;
    if (!completeButton.classList.contains("complete-task")) {
        return;
    }
    
    completeButton.innerText = "Updating...";

    const errorMessageDisplay = document.getElementById('error-message-task');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');
    console.log(JSON.stringify(completeButton.dataset));

    const response = await this.client.markComplete(completeButton.dataset.taskname, completeButton.dataset.tasktype, completeButton.dataset.difficulty, (error) => {
       errorMessageDisplay.innerText = `Error: ${error.message}`;
       errorMessageDisplay.classList.remove('hidden');
   });
   this.dataStore.set("user", response.data.userModel);
   this.dataStore.set("gold", response.data.gold + response.data.userModel.gold);

    const alertHTML =`<div class="alert alert-success fade show">
    <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
    <strong>Success!</strong> You earned ${response.data.gold} gold.
    </div>`
    document.getElementById('successfulAlert').innerHTML = alertHTML;
  }
     /**
          * when mark complete button is pushed, updates task.
          */
  async updateUserName(evt) {

        evt.preventDefault();

        
        const updateUserNameButton = document.getElementById('updateUserName');

        if (!updateUserNameButton.classList.contains("updateUserNameButton")) {
            return;
        }

        const origButtonText = updateUserNameButton.innerText;
        updateUserNameButton.innerText = 'Loading...';

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const displayName = document.getElementById('updated-name').value;

        const user = await this.client.updateUserName(displayName, (error) => {
            updateUserNameButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('user', user);
        updateUserNameButton.innerText = origButtonText;
    
   };
   //creates a new task
   async createNewTask(evt) {
    evt.preventDefault();
    

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const createTaskButton = document.getElementById('createTaskButton');
    if (!createTaskButton.classList.contains("createTask")) {
        return;
    }
    const origButtonText = createTaskButton.innerText;
    createTaskButton.innerText = 'Loading...';

    const taskName = document.getElementById('newTaskName').value;
    const newTaskType = document.getElementById('newTaskType').value;
    const newTaskDifficulty = document.getElementById('newTaskDifficulty').value;

    const user = await this.client.createNewTask(taskName, newTaskType, newTaskDifficulty, (error) => {
        createTaskButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('user', user);
    createTaskButton.innerText = origButtonText;
}

        /**
* Starts a new day by updating user tasks.
*/
async startNewDay(evt) {
    evt.preventDefault();

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const startNewDayButton = document.getElementById('startNewDayButton');
    const origButtonText = startNewDayButton.innerText;
    startNewDayButton.innerText = 'Loading...';

    const user = await this.client.startNewDay((error) => {
        startNewDayButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('user', user);
    startNewDayButton.innerText = origButtonText;
}

        /**
* Uses an item in the user inventory
*/
async useItem(e) {
    const useItemButton = e.target;

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const origButtonText = useItemButton.innerText;
    useItemButton.innerText = 'Loading...';

    const user = await this.client.useItem(useItemButton.dataset.assettype, useItemButton.dataset.assetid, useItemButton.dataset.assetdescription, useItemButton.dataset.assetname, (error) => {
        useItemButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('user', user);
    this.dataStore.set('inventory', user.inventory);
    useItemButton.innerText = origButtonText;
}

/**
    * When store button is pushed, adds store to page
    */
    addStoreToPage() {
        const inventory = this.dataStore.get('store');
        if (inventory == null) {
            return;
        }
        
        let inventoryHTML = '<div class="row row-cols-1 row-cols-md-3 g-4">';
        let assetCounter = 0;
        let asset;
        for (asset of inventory) {
            assetCounter++;
            inventoryHTML += `
            <div class="col">
                <div class="card h-100" style="width: 12rem;">
                    <img class="card-img-top" src="sprites/${asset.name}.png" alt="Card image cap">
                    <div class="card-body">
                    <h5 class="card-title">${asset.name}</h5>
                    <p class="card-text">${asset.description}<br>
                    ${asset.cost} Gold</p>
                    <button data-assetType="${asset.assetType}" data-assetID="${asset.assetId}" class="button buy-item">Buy Item</button>
                    </div>
                    </div>
                    </div>
            `;
        }
        inventoryHTML += `</div>`;
        document.getElementById('store-page').innerHTML = inventoryHTML;
    }

        /**
* Starts a new day by updating user tasks.
*/
async buyItem(e) {
    const buyItemButton = e.target;

    const errorMessageDisplay = document.getElementById('error-message');
    errorMessageDisplay.innerText = ``;
    errorMessageDisplay.classList.add('hidden');

    const origButtonText = buyItemButton.innerText;
    buyItemButton.innerText = 'Loading...';

    const user = await this.client.buyItem(buyItemButton.dataset.assettype, buyItemButton.dataset.assetid, (error) => {
        buyItemButton.innerText = origButtonText;
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
    });
    this.dataStore.set('user', user);
    this.dataStore.set('gold', user.gold);
    this.dataStore.set('inventory', user.inventory);
    buyItemButton.innerText = origButtonText;
}
}
// /**
//  * Main method to run when the page contents have loaded.
//  */
// const main = async () => {
//     const viewUser = new ViewUser();
//     viewUser.mount();
// };

//window.addEventListener('DOMContentLoaded', main);