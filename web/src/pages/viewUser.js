import TaskWarriorClient from '../api/taskWarriorClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the view user page of the website.
 */
class ViewUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addUserToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addUserToPage);
        this.header = new Header(this.dataStore);
        console.log("viewuser constructor");
    }

    /**
     * Once the client is loaded, get the user metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const userId = urlParams.get('id');
        document.getElementById('user-name').innerText = "Loading User ...";
        const user = await this.client.getUser(userId);
        this.dataStore.set('user', user);
    }

    /**
     * Add the header to the page and load the TaskWarriorClient.
     */
    mount() {
        document.getElementById('add-song').addEventListener('click', this.addSong);

        this.header.addHeaderToPage();

        this.client = new TaskWarriorClient();
        this.clientLoaded();
    }

    /**
     * When the user is updated in the datastore, update the user metadata on the page.
     */
    addUserToPage() {
        const user = this.dataStore.get('user');
        if (user == null) {
            return;
        }

        document.getElementById('user-name').innerText = user.name;
        document.getElementById('user-owner').innerText = user.customerName;

    }

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewUser = new ViewUser();
    viewUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
