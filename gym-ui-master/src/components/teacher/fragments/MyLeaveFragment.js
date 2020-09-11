import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TablePagination from "@material-ui/core/TablePagination";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import TableHead from "@material-ui/core/TableHead";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import PropTypes from "prop-types";
import Moment from "react-moment";
import axiosInstance from "../axiosInstance";


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
    },
    title: {
        padding: theme.spacing(2),
    },
    table: {
        minWidth: 750,
    },
    tableWrapper: {
        overflowX: 'auto',
    },
}));

const headCells = [
    {id: 'lstart', align: 'left', label: '请假开始日期'},
    {id: 'lend', align: 'left', label: '请假结束日期'},
    {id: 'admin', align: 'left', label: '审批人'},
    {id: 'lstate', align: 'left', label: '审批状态'},
    {id: 'operations', align: 'center', label: '操作'},
];

class MyLeaveFragment extends React.Component {

    constructor(props) {
        super(props);
        this.state = {leaveOrders: [], page: 0, rowsPerPage: 5};
    }

    componentDidMount() {
        axiosInstance.get('/leaveOrders/myLeaves').then(res => {
            this.setState({leaveOrders: res.data.leaveOrders});
        });
    }

    render() {
        const {classes} = this.props;

        const cancelLeaveOrderRequest = function (id) {
            axiosInstance.delete('/leaveOrders/cancelLeaveOrder/' + id).then((res) => {
                alert(res.data.message);
                // eslint-disable-next-line no-restricted-globals
                location.reload();
            }).catch((error) => {
                alert(error.response.data.message);
            });
        };

        return (<div className={classes.root}>
            <Paper className={classes.breadcrumbPaper}>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" component={RouterLink} to="/teacher/">首页</Link>
                    <Typography color="textPrimary">我的假条</Typography>
                </Breadcrumbs>
            </Paper>
            <Paper className={classes.paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle">
                    我的假条
                </Typography>
                <div className={classes.tableWrapper}>
                    <Table className={classes.table} aria-labelledby="tableTitle">
                        <TableHead>
                            <TableRow>
                                {headCells.map(headCell => (
                                    <TableCell
                                        key={headCell.id}
                                        align={headCell.align}
                                    >
                                        {headCell.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.leaveOrders.slice(
                                this.state.page * this.state.rowsPerPage,
                                (this.state.page + 1) * this.state.rowsPerPage)
                                .map((row, index) => {
                                    return (
                                        <TableRow hover tabIndex={-1} key={index}>
                                            <TableCell component="th" scope="row">
                                                <Moment format="YYYY-MM-DD" unix>{row.lstart / 1000}</Moment>
                                            </TableCell>
                                            <TableCell align="left">
                                                <Moment format="YYYY-MM-DD" unix>{row.lend / 1000}</Moment>
                                            </TableCell>
                                            <TableCell align="left">{row.admin ? row.admin.name : "待审批"}</TableCell>
                                            <TableCell align="left">{row.lstate}</TableCell>
                                            <TableCell align="center">
                                                <Button onClick={() => cancelLeaveOrderRequest(row.lno)}>销假</Button>
                                            </TableCell>
                                        </TableRow>
                                    );
                                })}

                        </TableBody>
                    </Table>
                </div>
                <TablePagination component="div" page={this.state.page} count={this.state.leaveOrders.length}
                                 rowsPerPage={this.state.rowsPerPage} rowsPerPageOptions={[5, 10, 25, 50]}
                                 onChangePage={(event, newPage) => this.setState({page: newPage})}
                                 onChangeRowsPerPage={(event) => {
                                     this.setState({rowsPerPage: parseInt(event.target.value), page: 0});
                                 }}/>
            </Paper>
        </div>)
    }
}

MyLeaveFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(MyLeaveFragment);