import TaskWarriorClient from '../api/taskWarriorClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class ViewUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addUserToPage', 'addTasksToPage', 'addInventoryToPage', 'addStatsToPage', 'delete'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addUserToPage);
        //this.dataStore.addChangeListener(this.addStoreToPage);
        this.dataStore.addChangeListener(this.addTasksToPage);
        this.dataStore.addChangeListener(this.addInventoryToPage);
        this.dataStore.addChangeListener(this.addStatsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewUser constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        document.getElementById('user-name').innerText = "loading user ...";
        const user = await this.client.getUser();
        this.dataStore.set('user', user);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        document.getElementById('delete-task').addEventListener('click', this.delete);
        this.header.addHeaderToPage();

        this.client = new TaskWarriorClient();
        this.clientLoaded();
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

    /**
     * adds user inventory to the page tab.
     */
    addInventoryToPage() {
        const user = this.dataStore.get('user');
        const inventory = user.inventory;
        if (inventory == null) {
            return;
        }
        
        let inventoryHTML = '<div class="row">';
        let assetCounter = 0;
        let asset;
        for (asset of inventory) {
            assetCounter++;
            inventoryHTML += `
                <div class="card" style="width: 12rem;">
                    <img class="card-img-top" src="sprites/${asset.name}.png" alt="Card image cap">
                    <div class="card-body">
                    <h5 class="card-title">${asset.name}</h5>
                    <p class="card-text">${asset.description}</p>
                    <a href="#" class="btn btn-primary">Use Item</a>
                    </div>
                    </div>
            `;
            if (assetCounter % 2 == 0) {
                inventoryHTML += `</div>
                <div class="row">
                `;
            }
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
        let taskHTML = '';
        let task;
        for (task of taskList) {
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
                        <button data-task="${task}" class="button update-task">Update</button>
                        </div>
                        <div class="col">
                        <button data-task="${task}" class="button delete-task">Delete</button>
                        </div>
                        <div class="col-5">
                        <button data-task="${task}" class="button complete-task">Mark Complete</button>
                        </div>
                    </div>
                </div>
            </div>
            `;
        }
        return taskHTML;
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

        await this.client.removeTaskFromList(deleteButton.dataset.task, (error) => {
           errorMessageDisplay.innerText = `Error: ${error.message}`;
           errorMessageDisplay.classList.remove('hidden');
       });

        document.getElementById(deleteButton.dataset.task).delete();
  }
/**
    * When store button is pushed, adds store to page
    */
    // addStoreToPage() {
    //     const store = this.dataStore.get('store');
    //     if (store == null) {
    //         return;
    //      }
    //     let storeHTML = '';
    //     let asset;
    //     for (asset of store.assets) {
    //         storeHTML += '<div class="tag">' + asset + '</div>';
    //     }
    //     document.getElementById('store').innerHTML = storeHTML;
    // }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewUser = new ViewUser();
    viewUser.mount();
};

window.addEventListener('DOMContentLoaded', main);