import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
/**
 * Logic needed for the view playlist page of the website.
 */
class ViewUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPlaylistToPage', 'addSongsToPage', 'addSong'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPlaylistToPage);
        this.dataStore.addChangeListener(this.addSongsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewUser constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const userId = urlParams.get('id');
//        document.getElementById('playlist-name').innerText = "Loading Playlist ...";
//        const playlist = await this.client.getPlaylist(playlistId);
//        this.dataStore.set('playlist', playlist);
//        document.getElementById('songs').innerText = "(loading songs...)";
//        const songs = await this.client.getPlaylistSongs(playlistId);
//        this.dataStore.set('songs', songs);
        const user = await this.client.getUser(userId);
        this.dataStore.set('user', user);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
        this.clientLoaded();
    }

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewUser = new ViewUser();
    viewUser.mount();
};

window.addEventListener('DOMContentLoaded', main);