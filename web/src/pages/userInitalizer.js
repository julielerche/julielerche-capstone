import DataStore from "../util/DataStore.js";
import TaskWarriorClient from "../api/taskWarriorClient.js";
import ViewUser from "./viewUser.js";
import ViewEncounter from "./viewEncounter.js";
import BindingClass from "../util/bindingClass";
import Header from '../components/header';
/**
 * Logic needed for the user page of the website.
 */
class UserInitalizer extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'clientLoaded'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }
    /**
 * Add the header to the page and load the TaskWarriorClient.
 */
    mount() {
        this.header.addHeaderToPage();

        this.client = new TaskWarriorClient();
        this.clientLoaded();
    }



async clientLoaded() {
    this.dataStore = new DataStore();
    const user = await this.client.getUser();
    this.dataStore.set('user', user);
    const viewUser = new ViewUser(this.client, this.dataStore);
    viewUser.mount();
    const viewEncounter = new ViewEncounter(this.client, this.dataStore);
    viewEncounter.mount();
}
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userInitalizer = new UserInitalizer();
    userInitalizer.mount();
};

window.addEventListener('DOMContentLoaded', main);