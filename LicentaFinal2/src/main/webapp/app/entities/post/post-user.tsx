import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, getEntitiesForUser, reset } from './post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PostUser = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const postList = useAppSelector(state => state.post.entities);
  const loading = useAppSelector(state => state.post.loading);
  const totalItems = useAppSelector(state => state.post.totalItems);
  const links = useAppSelector(state => state.post.links);
  const entity = useAppSelector(state => state.post.entity);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const account = useAppSelector(state => state.authentication.account);

  const getAllEntities = () => {
    dispatch(
      getEntitiesForUser({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
        query: account.id
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntitiesForUser({}))
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;


  return (
    <div>
      <h2 id="post-heading" data-cy="PostHeading">
        Your Posts
      </h2>
      <div className="table-responsive">
        
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {postList && postList.length > 0 ? (
            <Table responsive style={{ borderCollapse:'separate',
              borderSpacing:"0 15px"}}>
              <tbody>
                {postList.map((post, i) => (
                  <tr style={{backgroundImage:'linear-gradient(to right, LightSkyBlue,LightPink)'}} key={`entity-${i}`} data-cy="entityTable">
                   <td >
                     <p style={{color:'DimGrey'}}><b>{post.title}</b></p> <p>{post.description}</p>
                    {post.created_at ? <TextFormat type="date" value={post.created_at} format={APP_DATE_FORMAT}/> : null}                      
                    </td>
                    <td style={{textAlign:'end', verticalAlign:'bottom'}}>{post.category} - {post.subcategory}</td>
                    <td style={{textAlign:'end', alignItems:"end"}}>
                        <div className="btn-group flex-btn-group-container" style={{verticalAlign:'top'}}>
                        <Button tag={Link} to={{pathname: `${match.url}/${post.id}/edit`, state: {prevPath: location.pathname}}} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={{pathname: `/post/user-posts/${post.id}/delete`, state: {prevPath: location.pathname}}} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                      <div style={{height:'50px'}}></div>
                      <div style={{textAlign:'end',placeSelf :'bottom'}}> {post.judet} - {post.city} </div>
                    
                     </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Posts found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PostUser;
