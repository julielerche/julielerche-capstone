import DataStore from "../util/DataStore.js";
import TaskWarriorClient from "../api/taskWarriorClient.js";
import ViewUser from "./viewUser.js";
import ViewEncounter from "./viewEncounter.js";
/**
 * Logic needed for the user page of the website.
 */
class CreateUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'userInitalizer'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }



async clientLoaded() {
    const client = new TaskWarriorClient();
    this.dataStore = new DataStore();
    const user = await client.getUser();
    dataStore.set('user', user);
    const viewUser = new ViewUser(client, dataStore);
    viewUser.mount();
    const viewEncounter = new ViewEncounter(client, dataStore);
    viewEncounter.mount();
}
}
window.addEventListener('DOMContentLoaded', userInitalizer);