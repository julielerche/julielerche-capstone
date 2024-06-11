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
        this.bindClassMethods(['clientLoaded', 'mount', 'addUserToPage', 'addTasksToPage', 'addInventoryToPage', 'addStatsToPage'], this);
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
       // const dailies = await this.client.getUserTasks("DAILY");
        this.dataStore.set('user', user);
        //this.dataStore.set('dailies', dailies);
        //this.dataStore.set('store', store);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
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
        
        let inventoryHTML = '';
        let asset;
        for (asset of inventory) {
            inventoryHTML += `
                <li class="asset">
                    <span class="name">${asset.name}</span>
                    <span class="description">${asset.description}</span>
                </li>
            `;
        }
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

        let taskHTML = '';
        let task;
        for (task of dailies) {
            taskHTML += `
                <li class="task">
                    <span class="taskName">${task.taskName}</span>
                    <span class="difficulty">${task.difficulty}</span>
                </li>
            `;
        }
        document.getElementById('daily-tasks').innerHTML = taskHTML;

        const chores = user.chores;
        if (chores == null) {
            return;
        }

        let choreHTML = '';
        let chore;
        for (chore of chores) {
            choreHTML += `
                <li class="task">
                    <span class="taskName">${chore.taskName}</span>
                    <span class="difficulty">${chore.difficulty}</span>
                </li>
            `;
        }
        document.getElementById('chore-tasks').innerHTML = choreHTML;

        const todos = user.todos;
        if (todos == null) {
            return;
        }

        let todoHTML = '';
        let todo;
        for (todo of todos) {
            todoHTML += `
                <li class="task">
                    <span class="taskName">${todo.taskName}</span>
                    <span class="difficulty">${todo.difficulty}</span>
                </li>
            `;
        }
        document.getElementById('todo-tasks').innerHTML = todoHTML;
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