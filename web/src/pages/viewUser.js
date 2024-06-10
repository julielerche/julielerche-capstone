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
        this.bindClassMethods(['clientLoaded', 'mount', 'addUserToPage', 'addTasksToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addUserToPage);
        //this.dataStore.addChangeListener(this.addStoreToPage);
        this.dataStore.addChangeListener(this.addTasksToPage);
        this.header = new Header(this.dataStore);
        console.log("viewUser constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        document.getElementById('user-name').innerText = "loading user ...";
        const user = await this.client.getUser();
        const dailies = await this.client.getUserTasks("DAILY");
        this.dataStore.set('user', user);
        this.dataStore.set('dailies', dailies);
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
     * Gets user tasks in each tab
     */
    addTasksToPage() {
        const dailies = this.dataStore.get('dailies');
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