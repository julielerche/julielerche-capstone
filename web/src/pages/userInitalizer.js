import DataStore from "../util/DataStore.js";
import TaskWarriorClient from "../api/taskWarriorClient.js";
import ViewUser from "./viewUser.js";
import ViewEncounter from "./viewEncounter.js";

const userInitalizer = async () => {
    const client = new TaskWarriorClient();
    const datastore = new DataStore();
    const user = await client.getUser();
    datastore.set('user', user);
    const viewUser = new ViewUser(client, datastore);
    viewUser.mount();
    const viewEncounter = new ViewEncounter(client, datastore);
    viewEncounter.mount();
}
window.addEventListener('DOMContentLoaded', userInitalizer);