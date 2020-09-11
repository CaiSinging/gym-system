import Paper from "@material-ui/core/Paper";
import React from "react";
import Typography from "@material-ui/core/Typography";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import 'moment';
import {KeyboardDateTimePicker,} from '@material-ui/pickers';
import axiosInstance from "../axiosInstance";
import PropTypes from "prop-types";
import {withStyles} from "@material-ui/core";
import moment from "moment";

const styles = (theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    breadcrumbPaper: {
        marginBottom: theme.spacing(2),
        padding: theme.spacing(2),
    },
    paper: {
        width: '100%',
        marginBottom: theme.spacing(2),
        paddingBottom: theme.spacing(2),
    },
    title: {
        padding: theme.spacing(2),
    },

    visuallyHidden: {
        border: 0,
        clip: 'rect(0 0 0 0)',
        height: 1,
        margin: -1,
        overflow: 'hidden',
        padding: 0,
        position: 'absolute',
        top: 20,
        width: 1,
    },
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formInput: {
        width: '100%',
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2),
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1),
        width: 200,
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

class OrderTimeFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {selectedStartDate: new Date()};
    }

    render() {
        const handleStartDateChange = date => {
            this.setState({selectedStartDate: date});
        };
        const {classes, history, match} = this.props;
        const params = this.props.match.params;
        const orderTestRequest = () => {
            axiosInstance.put('/testOrders/orderTest/' + params.id, {
                selectedStartDate: moment(this.state.selectedStartDate).format("YYYY/MM/DD HH")
            }).then((res) => {
                alert(res.data.message);
                history.push(match.url);
            }).catch((error) => {
                alert(error.response.data.message);
            });
        };

        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/customer/">首页</Link>
                        <Link color="inherit" component={RouterLink} to="/customer/orderTest">预约体测</Link>
                        <Typography color="textPrimary">预约</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        预约时间
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <KeyboardDateTimePicker
                                    disableToolbar ampm={false}
                                    variant="inline"
                                    format="YYYY/MM/DD HH"
                                    margin="normal"
                                    label="预约时间"
                                    views={['year', 'month', 'date', 'hours']}
                                    value={this.state.selectedStartDate}
                                    onChange={handleStartDateChange}
                                />
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined"
                                        onClick={orderTestRequest}>确认</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        )
    }
}

OrderTimeFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(OrderTimeFragment);
