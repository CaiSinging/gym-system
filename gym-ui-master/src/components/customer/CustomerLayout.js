import React from 'react';
import Toolbar from '@material-ui/core/Toolbar';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import {makeStyles} from '@material-ui/core/styles';
import Copyright from "../common/Copyright";

const useStyles = makeStyles(theme => ({
    toolbar: {
        borderBottom: `1px solid ${theme.palette.divider}`,
    },
    toolbarTitle: {
        flex: 1,
    },
    toolbarSecondary: {
        justifyContent: 'space-between',
        overflowX: 'auto',
    },
    toolbarButton: {
        padding: theme.spacing(1),
        border: 'none',
    },
    mainContainer: {
        marginBottom: theme.spacing(6),
    },
    Container2:{
        backgroundImage: 'url(https://images.unsplash.com/photo-1534438327276-14e5300c3a48?auto=format&fit=crop&w=1280&q=60)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        height:'800px',
    },
    footer: {
        backgroundColor: '#f5f5f5',
        padding: theme.spacing(3),
    },
}));

function TabPanel(props) {
    const {children, value, index, ...other} = props;
    return (
        <Typography
            component="div"
            role="tabPanel"
            hidden={value !== index}
            id={`tabPanel-${index}`}
            aria-labelledby={`tab-${index}`}
            {...other}
        >
            <Box p={1}>{children}</Box>
        </Typography>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired,
};

export default function CustomerLayout(props) {
    const classes = useStyles();

    return (
        <React.Fragment>
            <Container maxWidth="lg" className={classes.mainContainer}>
                <Toolbar className={classes.toolbar}>
                    <Typography
                        component="h2"
                        variant="h5"
                        color="inherit"
                        align="center"
                        noWrap
                        className={classes.toolbarTitle}
                    >
                        主页
                    </Typography>
                    <Button variant="outlined" size="small" onClick={() => {
                        sessionStorage.removeItem("customerToken");
                        props.history.push(props.match.url);
                    }}>
                        退出登录
                    </Button>
                </Toolbar>
                <Container className={classes.Container2}>
                <main>
                    {/* Here goes sub-views */}
                    {props.children}
                </main>
                </Container>
            </Container>

            <footer className={classes.footer}>
                <Container maxWidth="lg" align="center">
                    <Copyright/>
                </Container>
            </footer>
        </React.Fragment>
    );
}
