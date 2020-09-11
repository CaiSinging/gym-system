import Typography from "@material-ui/core/Typography";
import Link from "@material-ui/core/Link";
import React from "react";

export default function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary">
            {'Copyright © '}
            {new Date().getFullYear()}
            {' '}
            <Link color="inherit" href="#">
                Your Website
            </Link>
            {'.'}
        </Typography>
    );
}