import {
  Links,
  Meta,
  Outlet,
  Scripts,
} from "@remix-run/react";
import { Screen } from "./routes/login";

// get the user data or redirect to /login if it failed
let user = await authenticator.isAuthenticated(request, {
  failureRedirect: "/login",
});

// if the user is authenticated, redirect to /dashboard
await authenticator.isAuthenticated(request, {
  successRedirect: "/",
});

export async function action({ request }: ActionFunctionArgs) {
  await authenticator.logout(request, { redirectTo: "/login" });
};


export default function App() {
  return (
    <html>
      <head>
        <link
          rel="icon"
          href="data:image/x-icon;base64,AA"
        />
        <Meta />
        <Links />
      </head>
      <body>
        <h1>Hello world!</h1>
        <Outlet />

        <Scripts />
      </body>
    </html>
  );
}
